package com.zhenai2.network

import com.zhenai2.common.Constants
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.ConcurrentHashMap

/**
 * 请求拦截器 —— 注入珍爱网要求的公共参数
 *
 * 来源: zhenai-2.0.2.min.js 的 Z.ajax 封装
 * 注入内容:
 *   GET 请求: ua/_/data 放入 URL query 参数
 *   POST 请求: ua/_/data 放入 POST 请求体 (与 H5 行为一致)
 *   header: Content-Type=application/x-www-form-urlencoded
 *   cookie: sid/token(登录态) + TDC_itoken/_efmdata(风控,由CookieJar管理)
 *
 * 注意: data 参数仅在设备指纹采集完成后才注入，避免空指纹导致 WAF 428 拦截。
 */
class RequestInterceptor(
    private val fingerprintProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val fp = fingerprintProvider()
        val timestamp = System.currentTimeMillis().toString()

        val request = if (original.method == "POST") {
            // POST 请求: 公共参数放入请求体 (与 H5 Z.ajax 行为一致)
            val newUrl = original.url.newBuilder()
                .addQueryParameter("_", timestamp)
                .build()

            // 读取原始表单字段(可能为null,如 checkLogin.do 等无参POST)
            val formBuilder = FormBody.Builder()
            val originalBody = original.body
            if (originalBody is FormBody) {
                for (i in 0 until originalBody.size) {
                    formBuilder.add(originalBody.name(i), originalBody.value(i))
                }
            }
            // 添加公共参数到请求体
            formBuilder.add("ua", ua())
            if (fp != null) {
                formBuilder.add("data", fp)
            }

            original.newBuilder()
                .url(newUrl)
                .post(formBuilder.build())
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Origin", "https://www.zhenai.com")
                .addHeader("Referer", "https://www.zhenai.com/")
                .addHeader("User-Agent", ua())
                .build()
        } else {
            // GET 请求: 公共参数放入 URL query
            val builder = original.url.newBuilder()
                .addQueryParameter("ua", ua())
                .addQueryParameter("_", timestamp)
            if (fp != null) {
                builder.addQueryParameter("data", fp)
            }

            original.newBuilder()
                .url(builder.build())
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Origin", "https://www.zhenai.com")
                .addHeader("Referer", "https://www.zhenai.com/")
                .addHeader("User-Agent", ua())
                .build()
        }

        return chain.proceed(request)
    }

    /**
     * User-Agent —— 必须与 FingerprintCollector 中 WebView 的 UA 完全一致
     *
     * 通盾指纹的 os 字段为 "web"(因通过 WebView 采集),
     * 若 User-Agent 声明为 Android App, WAF 交叉验证不匹配会返回 428。
     * 改用 Chrome 浏览器 UA, 使指纹类型(os:web)与请求 UA 一致。
     */
    private fun ua(): String =
        "Mozilla/5.0 (Linux; Android ${android.os.Build.VERSION.RELEASE}; ${android.os.Build.MODEL}) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
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
