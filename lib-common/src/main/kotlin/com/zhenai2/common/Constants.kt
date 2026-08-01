package com.zhenai2.common

/**
 * 全局常量 —— 来源于对珍爱网 H5 端明文 JS 的逆向分析
 *
 * 主机/路径前缀:
 *   H5 端: https://www.zhenai.com/api/   (与 api.zhenai.com 同源反代)
 *   App 端: https://api.zhenai.com/
 *   降级:  https://tapi.zhenai.com/ (+api_ip 参数)
 *   指纹:  https://secdffinger.zhenai.com
 *   H5:    https://i.zhenai.com
 *
 * 标准返回结构(实测 /api/login/getGeetestCaptcha.do 确认):
 *   { isError:Boolean, errorCode:String, errorMessage:String, data:Any }
 */
object Constants {

    // ---- 接口主机 ----
    // 使用 H5 端地址 (www.zhenai.com/api) 而非原生 App 地址 (api.zhenai.com)
    // 原因: api.zhenai.com 部署了 EdgeOne WAF,仅放行官方 App 的 TLS 指纹(JA3),
    //       第三方 OkHttp 请求会被 428 拦截。www.zhenai.com 是公开网站,
    //       WAF 规则相对宽松, H5 端 JS 本身也是通过此地址调用。
    const val API_HOST = "https://www.zhenai.com/api"
    const val API_HOST_H5 = "https://www.zhenai.com/api"
    const val API_HOST_FALLBACK = "https://tapi.zhenai.com"
    const val FINGER_HOST = "https://secdffinger.zhenai.com"
    const val H5_HOST = "https://i.zhenai.com"
    const val WEB_HOST = "https://www.zhenai.com"

    // ---- 业务错误码(来源于 login.js 错误处理分支) ----
    object ErrorCode {
        const val NOT_LOGIN = "-00004"        // 未登录/登录态失效 -> 跳登录页
        const val NEED_IMG_CODE = "-8001021"  // 需要图形验证码 -> captchaType=2
        const val NEED_ACTIVATE_PHONE = "-8001025" // 需要激活手机号
    }

    // ---- 验证码类型 ----
    object CaptchaType {
        const val TX_SLIDE = 1   // 腾讯滑块
        const val IMG_CODE = 2   // 图形验证码
    }

    // ---- Cookie key(来源于 zhenai-2.0.2.min.js Z.cookie) ----
    object CookieKey {
        const val SID = "sid"
        const val TOKEN = "token"
        const val UA = "ua"
        const val LOGIN_SEC = "_pc_login_sec"
        const val LOGIN_VALIDATE = "_pc_login_validate"
        const val LOGIN_IS_WEAK_PWD = "_pc_login_isWeakPwd"
        const val LOGIN_SECRET_PHONE = "_pc_login_secret_phone"
    }
}
