package com.zhenai2.network

import android.os.Environment
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 文件日志拦截器 —— 将 API 请求的地址、入参、返回结果写入 SD 卡文件
 *
 * 日志路径: /sdcard/douyinguanjia/Log/zhenai2.log
 */
class FileLoggerInterceptor : Interceptor {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val timestamp = dateFormat.format(Date())
        val sb = StringBuilder()

        // ---- 请求信息 ----
        sb.appendLine("=== [$timestamp] REQUEST ===")
        sb.appendLine("Method : ${request.method}")
        sb.appendLine("URL    : ${request.url}")

        // 请求头（排除敏感信息）
        sb.appendLine("Headers:")
        for (i in 0 until request.headers.size) {
            val name = request.headers.name(i)
            // 隐藏敏感 header 的值
            val value = if (name.equals("Cookie", ignoreCase = true) ||
                name.equals("Authorization", ignoreCase = true)
            ) {
                "[REDACTED]"
            } else {
                request.headers.value(i)
            }
            sb.appendLine("  $name: $value")
        }

        // 请求体 (POST 参数)
        val body = request.body
        if (body != null) {
            sb.appendLine("Body:")
            try {
                val buffer = Buffer()
                body.writeTo(buffer)
                val bodyStr = buffer.readUtf8()
                sb.appendLine(if (bodyStr.length > 2048) bodyStr.take(2048) + "..." else bodyStr)
            } catch (e: Exception) {
                sb.appendLine("  [body read error: ${e.message}]")
            }
        }
        sb.appendLine()

        // ---- 响应信息 ----
        val startNs = System.nanoTime()
        return try {
            val response = chain.proceed(request)
            val elapsed = (System.nanoTime() - startNs) / 1_000_000

            sb.appendLine("=== [$timestamp] RESPONSE ($elapsed ms) ===")
            sb.appendLine("Code   : ${response.code}")
            sb.appendLine("Message: ${response.message}")

            // 读取响应体（不破坏原始流）
            val responseBody = response.body
            if (responseBody != null) {
                val contentType = responseBody.contentType()
                val contentStr = responseBody.string()
                sb.appendLine("Content-Type: $contentType")
                sb.appendLine("Body:")
                sb.appendLine(if (contentStr.length > 4096) contentStr.take(4096) + "..." else contentStr)
                sb.appendLine()

                // 重建响应体（因为 string() 只能调用一次）
                val newBody = ResponseBody.create(contentType, contentStr)
                writeLog(sb.toString())

                response.newBuilder().body(newBody).build()
            } else {
                sb.appendLine("Body: [empty]")
                sb.appendLine()
                writeLog(sb.toString())
                response
            }
        } catch (e: Exception) {
            sb.appendLine("=== [$timestamp] ERROR ===")
            sb.appendLine("Exception: ${e.message}")
            sb.appendLine()
            writeLog(sb.toString())
            throw e
        }
    }

    private fun writeLog(content: String) {
        try {
            val logDir = File(Environment.getExternalStorageDirectory(), "douyinguanjia/Log")
            if (!logDir.exists()) {
                logDir.mkdirs()
            }
            val logFile = File(logDir, "zhenai2.log")
            logFile.appendText(content + "\n")
        } catch (e: Exception) {
            // 静默失败，不能影响主流程
        }
    }
}