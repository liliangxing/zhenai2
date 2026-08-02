package com.zhenai2.network

import com.zhenai2.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络客户端 —— Retrofit + OkHttp 单例
 *
 * 关键策略: 使用 [UrlConnectionInterceptor] 作为最内层应用拦截器,
 * 不调用 chain.proceed(),完全绕过 OkHttp 的 consCrypt TLS 实现,
 * 改用 Android 平台默认的 HttpURLConnection 发起请求,
 * 使 JA3 TLS 指纹与官方 App 一致,通过 EdgeOne WAF 检测。
 */
object NetworkClient {

    @Volatile private var api: ApiService? = null
    @Volatile private var fingerprint: String? = null
    val cookieJar = ZhenaiCookieJar()

    /** 设置设备指纹(由 App 启动时通过 secdffinger 采集后注入) */
    fun setFingerprint(fp: String?) { fingerprint = fp }

    val apiService: ApiService
        get() = api ?: synchronized(this) {
            api ?: build().also { api = it }
        }

    private fun build(): ApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(RequestInterceptor { fingerprint })
            .addInterceptor(FileLoggerInterceptor())
            .addInterceptor(logging)
            // UrlConnectionInterceptor 作为最内层应用拦截器,不调用 chain.proceed(),
            // 完全绕过 OkHttp 的 consCrypt TLS 实现,改用 HttpURLConnection
            .addInterceptor(UrlConnectionInterceptor(cookieJar))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.API_HOST + "/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
