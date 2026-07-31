package com.zhenai2.android

import android.content.Context
import android.os.Build
import android.os.Process
import com.zhenai2.common.FileLog

/**
 * 全局未捕获异常处理器 —— 闪退日志采集
 *
 * 日志写入路径(经 [FileLog]): /sdcard/douyinguanjia/Log/zhenai2.log
 * 降级: 应用专属外部存储 -> 应用内部缓存
 *
 * 实现说明:
 *   - 通过 Thread.setDefaultUncaughtExceptionHandler 安装,捕获任何线程未处理异常
 *   - 日志追加写入(append),每次闪退带时间戳分隔,便于排查
 *   - 写入完成后杀进程退出,避免残留状态
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private var defaultHandler: Thread.UncaughtExceptionHandler? = null

    fun install(ctx: Context) {
        // 先初始化统一日志工具(幂等),保证 CrashHandler 与业务模块写同一文件
        FileLog.init(ctx.applicationContext)
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        FileLog.i("CrashHandler 已安装")
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            FileLog.e("闪退 Thread=${t.name}(id=${t.id}) " +
                    "pid=${Process.myPid()} " +
                    "设备=${Build.MANUFACTURER} ${Build.MODEL} " +
                    "Android=${Build.VERSION.RELEASE}(sdk=${Build.VERSION.SDK_INT})", e)
        } catch (_: Throwable) {
            // 写日志本身不能再抛异常,否则会遮蔽原始崩溃
        }
        // 交回系统默认处理(通常弹"已停止"对话框并终止进程)
        defaultHandler?.uncaughtException(t, e)
        Process.killProcess(Process.myPid())
        System.exit(10)
    }

    companion object {
        @Volatile private var instance: CrashHandler? = null

        fun get(): CrashHandler =
            instance ?: synchronized(this) { instance ?: CrashHandler().also { instance = it } }
    }
}
