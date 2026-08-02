package com.zhenai2.network

import android.os.Environment
import com.zhenai2.common.FileLog
import okhttp3.Interceptor
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
 *
 * 诊断模式: 对非 2xx 响应(尤其 428 WAF 拦截)记录完整响应头和响应体,
 * 并通过 FileLog 输出摘要,便于用户在日志中直接看到 WAF 返回内容。
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

        // 输出到 FileLog: 完整 URL 含 query 参数,确认 data 参数是否存在
        val urlStr = request.url.toString()
        val hasData = urlStr.contains("data=") || (request.body != null && runCatching {
            val buffer = Buffer(); request.body!!.writeTo(buffer); buffer.readUtf8().contains("data=")
        }.getOrDefault(false))
        FileLog.i("REQUEST ${request.method} ${request.url.encodedPath} | data参数=${if (hasData) "有" else "无"}")

        // 请求头
        sb.appendLine("Headers:")
        for (i in 0 until request.headers.size) {
            val name = request.headers.name(i)
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

            // 完整记录响应头（诊断 WAF 关键信息）
            sb.appendLine("Response-Headers:")
            for (i in 0 until response.headers.size) {
                sb.appendLine("  ${response.headers.name(i)}: ${response.headers.value(i)}")
            }

            // 读取响应体（不破坏原始流）
            val responseBody = response.body
            val contentStr = if (responseBody != null) {
                val contentType = responseBody.contentType()
                sb.appendLine("Content-Type: $contentType")
                responseBody.string()
            } else {
                ""
            }

            val isError = response.code !in 200..299

            if (isError) {
                // 错误响应: 完整记录，不截断
                sb.appendLine("Body (full, error response):")
                sb.appendLine(contentStr)
                sb.appendLine()

                // 同时通过 FileLog 输出摘要，确保用户可见
                FileLog.w("HTTP ${response.code} ${response.message} | URL=${request.url.encodedPath}")
                FileLog.w("响应头摘要:")
                for (i in 0 until response.headers.size) {
                    FileLog.w("  ${response.headers.name(i)}: ${response.headers.value(i)}")
                }
                val bodyPreview = if (contentStr.length > 2000) contentStr.take(2000) + "..." else contentStr
                FileLog.w("响应体:\n$bodyPreview")
            } else {
                sb.appendLine("Body:")
                sb.appendLine(if (contentStr.length > 4096) contentStr.take(4096) + "..." else contentStr)
                sb.appendLine()
            }

            // 重建响应体
            val newBody = if (responseBody != null) {
                ResponseBody.create(responseBody.contentType(), contentStr)
            } else {
                null
            }
            writeLog(sb.toString())

            if (newBody != null) {
                response.newBuilder().body(newBody).build()
            } else {
                response
            }
        } catch (e: Exception) {
            sb.appendLine("=== [$timestamp] ERROR ===")
            sb.appendLine("Exception: ${e.javaClass.name}: ${e.message}")
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