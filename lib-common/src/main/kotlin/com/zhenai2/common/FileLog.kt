package com.zhenai2.common

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

/**
 * 日志文件工具 —— 统一写入 /sdcard/douyinguanjia/Log/zhenai2.log
 *
 * 供各业务模块(login/home/...)与 App 壳共用:
 *   FileLog.init(appContext)    在 Application.onCreate 最先调用
 *   FileLog.i("...") / w / e / d
 *
 * 降级链:  /sdcard/douyinguanjia/Log/zhenai2.log
 *         -> getExternalFilesDir/douyinguanjia/Log/zhenai2.log
 *         -> cacheDir/douyinguanjia/Log/zhenai2.log
 */
object FileLog {

    private const val TAG = "zhenai2"
    private const val DIR = "douyinguanjia/Log"
    private const val NAME = "zhenai2.log"

    private val executor = Executors.newSingleThreadExecutor()
    @Volatile private var appContext: Context? = null

    /** Application.onCreate 最早调用 */
    fun init(context: Context) {
        appContext = context.applicationContext
        i("========== 应用启动 ==========")
    }

    fun d(msg: String) = write("D", msg, null)
    fun i(msg: String) = write("I", msg, null)
    fun w(msg: String, tr: Throwable? = null) = write("W", msg, tr)
    fun e(msg: String, tr: Throwable? = null) = write("E", msg, tr)

    private fun write(level: String, msg: String, tr: Throwable?) {
        val sb = StringBuilder()
        sb.append("[${ts()}] $level $msg")
        tr?.let {
            val sw = StringWriter()
            it.printStackTrace(PrintWriter(sw))
            sb.append("\n").append(sw.toString().trimEnd())
        }
        val content = sb.append("\n").toString()
        // 同步输出到 logcat 便于开发期观察
        when (level) {
            "E" -> Log.e(TAG, msg, tr)
            "W" -> Log.w(TAG, msg, tr)
            else -> Log.i(TAG, msg)
        }
        executor.execute { persist(content) }
    }

    private fun persist(content: String) {
        val ctx = appContext ?: return

        val sdDir = File(Environment.getExternalStorageDirectory(), DIR)
        if (writeFile(sdDir, content)) return

        try {
            val fallback = File(ctx.getExternalFilesDir(null), DIR)
            if (writeFile(fallback, content)) return
        } catch (_: Throwable) {
        }

        try {
            val cache = File(ctx.cacheDir, DIR)
            writeFile(cache, content)
        } catch (_: Throwable) {
        }
    }

    private fun writeFile(dir: File, content: String): Boolean {
        return try {
            if (dir.exists() || dir.mkdirs()) {
                File(dir, NAME).appendText(content)
                true
            } else false
        } catch (e: Throwable) {
            Log.e(TAG, "写入日志失败: $dir/$NAME", e)
            false
        }
    }

    private fun ts(): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Date())
}
