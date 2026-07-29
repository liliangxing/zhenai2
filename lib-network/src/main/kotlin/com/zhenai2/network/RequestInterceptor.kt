package com.zhenai2.network

import com.zhenai2.common.Constants
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.ConcurrentHashMap

/**
 * 请求拦截器 —— 注入珍爱网要求的公共参数
 *
 * 来源: zhenai-2.0.2.min.js 的 Z.ajax 封装
 * 注入内容:
 *   query: ua(User-Agent标识), _(时间戳防缓存), data(设备指纹screenPrint)
 *   降级: api_ip(降级到 tapi.zhenai.com 时携带)
 *   header: Content-Type=application/x-www-form-urlencoded
 *   cookie: sid/token(登录态) + TDC_itoken/_efmdata(风控,由CookieJar管理)
 */
class RequestInterceptor(
    private val fingerprintProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url

        val builder = url.newBuilder()
            .addQueryParameter("ua", ua())
            .addQueryParameter("_", System.currentTimeMillis().toString())

        fingerprintProvider()?.let { builder.addQueryParameter("data", it) }

        val newUrl = builder.build()

        val request = original.newBuilder()
            .url(newUrl)
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            .addHeader("Accept", "application/json, text/plain, */*")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .build()

        return chain.proceed(request)
    }

    private fun ua(): String =
        "zhenai2/1.0.0 (Android ${android.os.Build.VERSION.RELEASE})"
}

/**
 * 简易 CookieJar —— 持久化 sid/token 及风控 cookie
 * 对应原 App 的会话 Cookie 管理(sid, token, TDC_itoken, _efmdata, _exid)
 */
class ZhenaiCookieJar : CookieJar {

    private val store = ConcurrentHashMap<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        store[url.host]?.let { it.removeAll(cookies); it.addAll(cookies) }
            ?: store.put(url.host, cookies.toMutableList())
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> =
        store[url.host] ?: emptyList()
}
