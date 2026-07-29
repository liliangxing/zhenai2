package com.zhenai2.network

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 珍爱网 API 服务接口定义
 *
 * 来源: 反编译珍爱网 H5 端明文 JS (login.js + zhenai-2.0.2.min.js)
 * 路径前缀: /api (H5 同源) 或直接 api.zhenai.com
 * 公共参数(ua/时间戳_/设备指纹data)由 RequestInterceptor 注入
 *
 * 调用顺序(App 打开主页):
 *   1. appConfig.do          App 全局配置
 *   2. checkLogin.do         检查登录态
 *   3. getConfigureInfo.do   登录页配置
 *   4. userLogin.do          账号密码登录(若未登录)
 *   5. getBasicProfile.do    用户基本资料(登录后)
 *   6. logTransferDc.do      埋点上报(持续)
 */
interface ApiService {

    // ============ 系统/启动 ============

    /** App 全局配置(含 interceptList 拦截策略) */
    @GET("system/appConfig.do")
    suspend fun appConfig(): ApiResponse<AppConfig>

    /** 检查当前会话是否已登录 */
    @GET("login/checkLogin.do")
    suspend fun checkLogin(): ApiResponse<LoginStatus>

    /** 登录页配置(是否需验证码/腾讯滑块) */
    @GET("system/getConfigureInfo.do")
    suspend fun getConfigureInfo(): ApiResponse<LoginConfig>

    // ============ 登录 ============

    /** 获取腾讯验证码 AppId(滑块/拼图) */
    @GET("login/getGeetestCaptcha.do")
    suspend fun getGeetestCaptcha(): ApiResponse<CaptchaConfig>

    /** 账号密码登录
     * @param phone 手机号
     * @param password 加密后的密码
     * @param captchaType 验证码类型 1=腾讯滑块 2=图形
     * @param imgCode 图形验证码(captchaType=2)
     * @param ticket 腾讯滑块票据(captchaType=1)
     * @param randstr 腾讯滑块随机串(captchaType=1)
     */
    @FormUrlEncoded
    @POST("login/userLogin.do")
    suspend fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("captchaType") captchaType: Int = 1,
        @Field("imgCode") imgCode: String = "",
        @Field("ticket") ticket: String = "",
        @Field("randstr") randstr: String = ""
    ): ApiResponse<LoginResult>

    /** 发送动态密码短信验证码 */
    @FormUrlEncoded
    @POST("login/sendDynamicMessageCode.do")
    suspend fun sendDynamicMessageCode(@Field("phone") phone: String): ApiResponse<SendCodeResult>

    /** 动态密码登录 */
    @FormUrlEncoded
    @POST("login/dynamicLogin.do")
    suspend fun dynamicLogin(
        @Field("phone") phone: String,
        @Field("messageCode") messageCode: String
    ): ApiResponse<LoginResult>

    /** 异地登录发短信 */
    @POST("login/sendNonlocalMsgCode.do")
    suspend fun sendNonlocalMsgCode(): ApiResponse<SendCodeResult>

    /** 异地登录验证 */
    @FormUrlEncoded
    @POST("login/submitNonlocalMsgCode.do")
    suspend fun submitNonlocalMsgCode(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): ApiResponse<LoginResult>

    /** 激活手机号发短信 */
    @FormUrlEncoded
    @POST("login/sendActivatePhoneMessageCode.do")
    suspend fun sendActivatePhoneMessageCode(@Field("phone") phone: String): ApiResponse<SendCodeResult>

    /** 激活手机号 */
    @FormUrlEncoded
    @POST("login/activatePhone.do")
    suspend fun activatePhone(
        @Field("phone") phone: String,
        @Field("messageCode") messageCode: String
    ): ApiResponse<LoginResult>

    // ============ 用户资料 ============

    /** 获取当前用户基本资料(登录后必调,基础库硬编码) */
    @POST("profile/getBasicProfile.do")
    suspend fun getBasicProfile(): ApiResponse<BasicProfile>

    // ============ 埋点/监控 ============

    /** 埋点日志上报(data 为 JSON 数组字符串) */
    @FormUrlEncoded
    @POST("log/logTransferDc.do")
    suspend fun logTransferDc(@Field("data") data: String): ApiResponse<Any>

    /** 前端监控采集 */
    @FormUrlEncoded
    @POST("monitor/collect.do")
    suspend fun monitorCollect(@Field("data") data: String): ApiResponse<Any>
}
