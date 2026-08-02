package com.zhenai2.network

import com.zhenai2.common.Constants
import kotlinx.coroutines.delay
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * 网络客户端 —— Retrofit + OkHttp 单例
 */
object NetworkClient {

    @Volatile private var api: ApiService? = null
    @Volatile private var fingerprint: String? = null
    val cookieJar = ZhenaiCookieJar()

    private val fingerprintLatch = CountDownLatch(1)

    /** 设置设备指纹(由 App 启动时异步采集后注入), null不触发countDown */
    fun setFingerprint(fp: String?) {
        fingerprint = fp
        if (fp != null) {
            fingerprintLatch.countDown()
        }
    }

    /** 标记指纹采集完成(无论成功失败) */
    fun markFingerprintDone() {
        fingerprintLatch.countDown()
    }

    /** 等待指纹采集完成,超时15秒 */
    suspend fun awaitFingerprint() {
        try {
            fingerprintLatch.await(16, TimeUnit.SECONDS)
        } catch (_: InterruptedException) {
            // 超时也继续,不阻塞启动流程
        }
    }

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
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
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
