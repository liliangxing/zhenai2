package com.zhenai2.network

import com.zhenai2.common.Constants
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext

/**
 * 网络客户端 —— Retrofit + OkHttp 单例
 *
 * 关键策略:
 * 1. 使用 [PlatformSSLSocketFactory] 阻止 OkHttp 过滤密码套件,
 *    保持 JA3 指纹与官方 App 一致。
 * 2. 限制协议为 HTTP/1.1,禁用 HTTP/2,匹配官方 App 的 ALPN 行为。
 * 3. 使用 H5 端点 (www.zhenai.com/api) 以降低 WAF 检测强度。
 */
object NetworkClient {

    @Volatile private var api: ApiService? = null
    @Volatile private var fingerprint: String? = null
    val cookieJar = ZhenaiCookieJar()

    /** 设置设备指纹(由 App 启动时异步采集后注入) */
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
            // 使用 PlatformSSLSocketFactory 阻止 OkHttp 过滤密码套件
            .sslSocketFactory(platformSslFactory, platformSslFactory.trustManager())
            // 限制为 HTTP/1.1,禁用 HTTP/2 (匹配官方 App 的 TLS ALPN)
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            // 宽松的 ConnectionSpec,让 PlatformSSLSocketFactory 接管密码套件选择
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
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
