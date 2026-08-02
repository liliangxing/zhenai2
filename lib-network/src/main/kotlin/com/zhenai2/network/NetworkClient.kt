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
 * 重要: 本应用使用 OkHttp 默认的 consCrypt TLS 实现。
 * 珍爱网 api.zhenai.com 的 EdgeOne WAF 通过 JA3 TLS 指纹识别客户端,
 * 官方 App 的 OkHttp 指纹已登记。本应用使用 OkHttp 默认配置,
 * 其 JA3 指纹与官方 App 一致(均为 OkHttp 标准 consCrypt 指纹)。
 * 若仍被拦截,需检查 OkHttp 版本是否与官方 App 一致。
 */
object NetworkClient {

    @Volatile private var api: ApiService? = null
    @Volatile private var fingerprint: String? = null
    private val cookieJar = ZhenaiCookieJar()

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
