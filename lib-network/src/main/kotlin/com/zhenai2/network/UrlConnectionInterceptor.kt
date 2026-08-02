package com.zhenai2.network

import okhttp3.Cookie
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.Buffer
import java.net.HttpURLConnection
import java.net.URL

/**
 * HttpURLConnection 网络拦截器
 *
 * 核心作用: 替换 OkHttp 的 consCrypt TLS 实现,改用 Android 平台默认的
 * HttpURLConnection 发起 HTTP 请求,从而改变 JA3 TLS 指纹。
 *
 * 背景:
 * OkHttp 4.x 内置 consCrypt TLS 实现,其 TLS 握手行为(密码套件选择、扩展顺序等)
 * 产生特定的 JA3 指纹。EdgeOne WAF 使用 JA3 指纹识别客户端合法性。
 * 本拦截器完全绕过 OkHttp 传输层,让平台默认的 TLS 栈处理握手,
 * 产生与官方 App 一致的 JA3 指纹。
 *
 * 工作方式:
 * - 注册为 OkHttp NetworkInterceptor
 * - 不调用 chain.proceed(),完全绕过 OkHttp 传输层
 * - 手动管理 Cookie (从 ZhenaiCookieJar 加载/保存)
 * - 使用 HttpURLConnection 执行 HTTP 请求
 */
class UrlConnectionInterceptor(
    private val cookieJar: ZhenaiCookieJar
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url

        // 1. 创建 HttpURLConnection
        val conn = URL(httpUrl.toString()).openConnection() as HttpURLConnection

        // 2. 复制超时设置
        conn.connectTimeout = chain.connectTimeoutMillis()
        conn.readTimeout = chain.readTimeoutMillis()

        // 3. 设置请求方法
        conn.requestMethod = request.method
        conn.doInput = true

        // 4. 设置请求头
        request.headers.forEach { header ->
            conn.setRequestProperty(header.first, header.second)
        }

        // 5. 手动添加 Cookie (绕过 BridgeInterceptor)
        val cookies = cookieJar.loadForRequest(httpUrl)
        if (cookies.isNotEmpty()) {
            val cookieStr = cookies.joinToString("; ") { "${it.name}=${it.value}" }
            // 覆盖可能已由 BridgeInterceptor 设置的 Cookie 头
            conn.setRequestProperty("Cookie", cookieStr)
        }

        // 6. 写入请求体 (POST/PUT)
        val body = request.body
        if (body != null && body.contentLength() != 0L) {
            conn.doOutput = true
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.inputStream().use { input ->
                conn.outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }

        // 7. 不自动跟随重定向 (与 OkHttp 默认行为一致)
        conn.instanceFollowRedirects = false

        // 8. 读取响应码和消息
        val responseCode = conn.responseCode
        val responseMessage = conn.responseMessage ?: ""

        // 9. 读取响应头
        val headersBuilder = Headers.Builder()
        conn.headerFields?.forEach { (key, values) ->
            if (key != null && values != null) {
                for (value in values) {
                    headersBuilder.add(key, value)
                }
            }
        }

        // 10. 保存 Cookie (解析 Set-Cookie 响应头)
        val setCookieValues = conn.headerFields?.entries
            ?.filter { it.key?.equals("Set-Cookie", ignoreCase = true) == true }
            ?.flatMap { it.value.orEmpty() } ?: emptyList()
        if (setCookieValues.isNotEmpty()) {
            val parsedCookies = setCookieValues.mapNotNull { setCookieHeader ->
                try {
                    Cookie.parse(httpUrl, setCookieHeader)
                } catch (_: Exception) { null }
            }
            if (parsedCookies.isNotEmpty()) {
                cookieJar.saveFromResponse(httpUrl, parsedCookies)
            }
        }

        // 11. 读取响应体
        val responseBytes = try {
            conn.inputStream?.use { it.readBytes() } ?: ByteArray(0)
        } catch (_: Exception) {
            conn.errorStream?.use { it.readBytes() } ?: ByteArray(0)
        }

        // 12. 构建 OkHttp Response
        val contentType = conn.contentType
        val responseBody = ResponseBody.create(
            contentType?.toMediaTypeOrNull(),
            responseBytes
        )

        conn.disconnect()

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(responseCode)
            .message(responseMessage)
            .headers(headersBuilder.build())
            .body(responseBody)
            .build()
    }
}