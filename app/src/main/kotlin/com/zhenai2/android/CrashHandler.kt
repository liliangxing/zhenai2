package com.zhenai2.android

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.Process
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 全局未捕获异常处理器 —— 闪退日志采集
 *
 * 日志写入路径: /sdcard/douyinguanjia/Log/zhenai2.log
 *
 * 实现说明:
 *   - 通过 Thread.setDefaultUncaughtExceptionHandler 安装,捕获任何线程未处理异常
 *   - 日志追加写入(append),每次闪退带时间戳分隔,便于排查
 *   - 写入完成后杀进程退出,避免残留状态
 *   - 存储权限: Manifest 已声明 READ/WRITE_EXTERNAL_STORAGE +
 *     requestLegacyExternalStorage=true,Android 9 及以下可直接写 sdcard 根目录;
 *     Android 10+ 受分区存储限制,实际写入位置为外部存储公共目录,
 *     若设备不允许写 /sdcard 根则降级到应用专属外部目录(getExternalFilesDir)。
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private var context: Context? = null
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null

    fun install(ctx: Context) {
        context = ctx.applicationContext
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            writeCrashLog(t, e)
        } catch (_: Throwable) {
            // 写日志本身不能再抛异常,否则会遮蔽原始崩溃
        }
        // 交回系统默认处理(通常弹"已停止"对话框并终止进程)
        defaultHandler?.uncaughtException(t, e)
        Process.killProcess(Process.myPid())
        System.exit(10)
    }

    /** 写入闪退日志到 /sdcard/douyinguanjia/Log/zhenai2.log */
    private fun writeCrashLog(t: Thread, e: Throwable) {
        val ctx = context ?: return
        val ts = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Date())

        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        val stack = sw.toString()

        val sb = StringBuilder()
        sb.append("========== CRASH $ts ==========\n")
        sb.append("Thread: ${t.name} (id=${t.id})\n")
        sb.append("Process: ${ctx.packageName} pid=${Process.myPid()}\n")
        sb.append("Device: ${Build.MANUFACTURER} ${Build.MODEL}\n")
        sb.append("Android: ${Build.VERSION.RELEASE} (sdk=${Build.VERSION.SDK_INT})\n")
        sb.append("App: ${getAppVersion(ctx)}\n")
        sb.append("Exception: ${e.javaClass.name}: ${e.message}\n")
        sb.append("Stacktrace:\n").append(stack).append("\n")
        sb.append("=================================\n\n")

        val content = sb.toString()

        // 1. 首选: /sdcard/douyinguanjia/Log/zhenai2.log (用户指定路径)
        val sdRoot = Environment.getExternalStorageDirectory()
        val targetDir = File(sdRoot, "douyinguanjia/Log")
        val targetFile = File(targetDir, "zhenai2.log")
        var written = false
        try {
            if (targetDir.exists() || targetDir.mkdirs()) {
                targetFile.appendText(content)
                written = true
            }
        } catch (_: Throwable) {
            // sdcard 根目录不可写(分区存储/权限),走降级
        }

        // 2. 降级: 应用专属外部存储(无需权限,但路径与用户指定不同)
        if (!written) {
            try {
                val fallbackDir = File(ctx.getExternalFilesDir(null), "douyinguanjia/Log")
                if (fallbackDir.exists() || fallbackDir.mkdirs()) {
                    File(fallbackDir, "zhenai2.log").appendText(content)
                }
            } catch (_: Throwable) {
                // 兜底: 应用内部缓存(绝对可写,但用户不可直接在 sd 卡看到)
                try {
                    val cacheDir = File(ctx.cacheDir, "douyinguanjia/Log")
                    cacheDir.mkdirs()
                    File(cacheDir, "zhenai2.log").appendText(content)
                } catch (_: Throwable) { /* 放弃 */ }
            }
        }
    }

    private fun getAppVersion(ctx: Context): String {
        return try {
            val pm = ctx.packageManager
            val info = pm.getPackageInfo(ctx.packageName, 0)
            "${info.versionName} (${info.longVersionCode})"
        } catch (e: Throwable) {
            "unknown"
        }
    }

    companion object {
        @Volatile private var instance: CrashHandler? = null

        fun get(): CrashHandler =
            instance ?: synchronized(this) { instance ?: CrashHandler().also { instance = it } }
    }
}
