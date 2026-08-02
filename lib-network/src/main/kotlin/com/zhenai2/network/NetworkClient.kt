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
 * 关键策略: 使用 [PlatformSSLSocketFactory] 阻止 OkHttp 的 ConnectionSpec
 * 过滤密码套件,让底层 Socket 使用平台默认的 consCrypt 完整密码套件集。
 * 这样 JA3 指纹与官方 App 一致,可通过 EdgeOne WAF 检测。
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

        val platformSslFactory = PlatformSSLSocketFactory()

        val client = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(RequestInterceptor { fingerprint })
            .addInterceptor(FileLoggerInterceptor())
            .addInterceptor(logging)
            .sslSocketFactory(platformSslFactory, platformSslFactory.trustManager())
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
