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
 * 主机: https://api.zhenai.com (App 原生)
 * 复刻说明: 原 App 使用 OkHttp + 自有封装,这里用等价的 Retrofit 实现,
 *          保留同样的公共参数注入(ua/_/data)与 Cookie 管理。
 *
 * 注意: 珍爱网部署 EdgeOne WAF,纯脚本请求会因 TLS 指纹(JA3)不符被 428 拦截。
 *       真机抓包显示 App 原生请求可正常通过(因 App TLS 栈与 WAF 已登记)。
 *       本复刻项目在真机运行时由 OkHttp/Conscrypt 提供的 TLS 栈发起请求,
 *       是否被 WAF 放行取决于设备指纹一致性,需真机验证。
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
