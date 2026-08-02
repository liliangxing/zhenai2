package com.zhenai2.network

import com.zhenai2.common.Constants
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * 网络客户端 —— Retrofit + OkHttp 单例
 *
 * 主机: https://www.zhenai.com/api (H5 端)
 *
 * WAF 428 说明:
 * 珍爱网部署了 EdgeOne WAF,通过 TLS 指纹(JA3)识别客户端合法性。
 * 官方 App 的 JA3 指纹已登记,第三方 OkHttp 请求指纹不同被拦截。
 * 以下策略尝试绕过:
 *   1. 仅使用 HTTP/1.1 (改变 TLS ALPN 扩展)
 *   2. 使用平台默认 SSLContext (避免 OkHttp 的 ConnectionSpec 过滤密码套件)
 *   3. 使用 H5 端点 www.zhenai.com/api (避开原生 api.zhenai.com)
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

        // 使用平台默认 SSLContext,不经过 OkHttp ConnectionSpec 过滤密码套件
        val trustManager = try {
            val ctx = SSLContext.getInstance("TLS")
            ctx.init(null, null, SecureRandom())
            ctx.socketFactory
            val tm = javax.net.ssl.TrustManagerFactory.getInstance(
                javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
            ).apply { init(null as java.security.KeyStore?) }
            tm.trustManagers.filterIsInstance<X509TrustManager>().first()
        } catch (e: Exception) {
            null
        }

        val clientBuilder = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(RequestInterceptor { fingerprint })
            .addInterceptor(FileLoggerInterceptor())
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            // 仅使用 HTTP/1.1,改变 TLS ALPN 扩展以匹配原 App 的 JA3 指纹
            .protocols(listOf(Protocol.HTTP_1_1))
            // 使用兼容 ConnectionSpec,包含更多密码套件
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))

        // 若平台默认 SSLContext 可用,替换 OkHttp 的默认 SSLSocketFactory
        trustManager?.let { tm ->
            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, null, SecureRandom())
                clientBuilder.sslSocketFactory(sslContext.socketFactory, tm)
            } catch (_: Exception) {}
        }

        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(Constants.API_HOST + "/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
