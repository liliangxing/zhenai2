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
 * 主机: https://www.zhenai.com/api (H5 端,避免原生 api.zhenai.com 的 WAF 428 拦截)
 * 复刻说明: 原 App 使用 OkHttp + 自有封装,这里用等价的 Retrofit 实现,
 *          保留同样的公共参数注入(ua/_/data)与 Cookie 管理。
 *
 * 注意: 珍爱网原生 api.zhenai.com 部署了 EdgeOne WAF,仅放行官方 App 的 TLS 指纹(JA3),
 *       第三方 OkHttp 请求会被 428 拦截。改用 H5 端 www.zhenai.com/api 地址,
 *       该地址为公开网站, WAF 规则相对宽松, H5 端 JS 本身也是通过此地址调用。
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
