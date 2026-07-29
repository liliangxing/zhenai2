package com.zhenai2.network

import com.google.gson.annotations.SerializedName

/**
 * 珍爱网标准 API 返回结构
 *
 * 实测确认来源: GET https://www.zhenai.com/api/login/getGeetestCaptcha.do
 * 返回: {"data":"","errorCode":"","errorMessage":"","isError":false}
 *
 * 所有 /api/ 下的 .do 接口统一此结构。
 */
data class ApiResponse<T>(
    @SerializedName("isError") val isError: Boolean = false,
    @SerializedName("errorCode") val errorCode: String? = null,
    @SerializedName("errorMessage") val errorMessage: String? = null,
    @SerializedName("data") val data: T? = null
)

// ---- 业务数据模型(各接口 data 字段) ----

/** checkLogin.do 返回 */
data class LoginStatus(
    @SerializedName("isLogin") val isLogin: Boolean = false
)

/** getConfigureInfo.do 返回 */
data class LoginConfig(
    @SerializedName("useVerifyCode") val useVerifyCode: Boolean = false,
    @SerializedName("useTxCode") val useTxCode: Boolean = false
)

/** appConfig.do 返回 */
data class AppConfig(
    @SerializedName("interceptList") val interceptList: List<Int>? = null
)

/** getGeetestCaptcha.do 返回 */
data class CaptchaConfig(
    @SerializedName("captchaAppId") val captchaAppId: String? = null,
    @SerializedName("success") val success: Int = 0
)

/** userLogin.do 返回 */
data class LoginResult(
    @SerializedName("token") val token: String? = null,
    @SerializedName("memberID") val memberID: Long = 0,
    @SerializedName("sid") val sid: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("isWeakPwd") val isWeakPwd: Boolean = false
)

/** sendDynamicMessageCode.do 返回 */
data class SendCodeResult(
    @SerializedName("phone") val phone: String? = null
)

/** getBasicProfile.do 返回(用户基本资料) */
data class BasicProfile(
    @SerializedName("memberID") val memberID: Long = 0,
    @SerializedName("nickname") val nickname: String? = null,
    @SerializedName("sex") val sex: Int = 0,
    @SerializedName("age") val age: Int = 0,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("isVip") val isVip: Boolean = false,
    @SerializedName("certStatus") val certStatus: Int = 0
)
