package com.zhenai2.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zhenai2.common.AccountManager
import com.zhenai2.common.base.BaseViewModel
import com.zhenai2.common.Constants
import com.zhenai2.common.FileLog
import com.zhenai2.network.CaptchaConfig
import com.zhenai2.network.LoginConfig
import com.zhenai2.network.LoginResult
import com.zhenai2.network.NetworkClient
import kotlinx.coroutines.launch

/**
 * 登录 ViewModel —— 完整复刻珍爱网登录流程
 *
 * 流程(对应 login.js 的 checkNeedValidate / submit / callback 链):
 *   1. 页面加载: checkLogin.do + getConfigureInfo.do
 *      - getConfigureInfo.useVerifyCode=true 且 useTxCode=false → 图形验证码(captchaType=2)
 *      - useTxCode=true → 腾讯滑块(captchaType=1, 需 ticket+randstr)
 *   2. 点击登录: userLogin.do(phone, 加密password, captchaType, imgCode|ticket+randstr)
 *   3. 错误处理:
 *      - -8001021: 需图形验证码 → captchaType=2 弹图形码
 *      - -8001025: 需激活手机号 → 走 activatePhone 流程
 *      - -00004:   未登录 → 重新登录
 *   4. 成功: 保存 token/memberID/sid,跳主页
 */
class LoginViewModel : BaseViewModel() {

    private val api get() = NetworkClient.apiService

    private val _loginConfig = MutableLiveData<LoginConfig>()
    val loginConfig: LiveData<LoginConfig> = _loginConfig

    private val _captchaConfig = MutableLiveData<CaptchaConfig>()
    val captchaConfig: LiveData<CaptchaConfig> = _captchaConfig

    private val _needImgCode = MutableLiveData<Boolean>()
    val needImgCode: LiveData<Boolean> = _needImgCode

    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    /** 页面加载: 拉取登录配置 + 验证码配置 */
    fun loadConfig() {
        launch(onError = { e ->
            FileLog.e("登录配置拉取异常", e)
        }) {
            // 对应 login.js mounted: kr("/system/getConfigureInfo.do")
            val config = api.getConfigureInfo().takeIf { !it.isError }?.data
            config?.let {
                _loginConfig.value = it
                if (it.useVerifyCode && !it.useTxCode) {
                    _needImgCode.value = true
                }
            }
            // 对应 login.js: Fr("/login/getGeetestCaptcha.do")
            val captcha = api.getGeetestCaptcha().takeIf { !it.isError }?.data
            captcha?.let { _captchaConfig.value = it }
        }
    }

    /**
     * 账号密码登录
     * @param phone 手机号
     * @param password 明文密码(加密在内部完成)
     * @param captchaType 1=腾讯滑块 2=图形
     * @param imgCode 图形验证码
     * @param ticket 腾讯滑块票据
     * @param randstr 腾讯滑块随机串
     */
    fun login(
        phone: String,
        password: String,
        captchaType: Int = Constants.CaptchaType.TX_SLIDE,
        imgCode: String = "",
        ticket: String = "",
        randstr: String = ""
    ) {
        if (phone.isBlank() || password.isBlank()) {
            _toast.value = "请输入手机号和密码"
            return
        }
        launch(onError = { e ->
            FileLog.e("登录请求异常", e)
            _toast.value = "网络异常: ${e.message}"
        }) {
            // 密码加密: 原 App 用 RSA/自定义加密,这里用摘要占位(真实加密需脱壳后还原)
            val encryptedPwd = encryptPassword(password)
            FileLog.i("发起 userLogin.do 登录请求 phone=${phone}")
            // 对应 login.js: Fr("/login/userLogin.do", {...})
            val resp = api.userLogin(phone, encryptedPwd, captchaType, imgCode, ticket, randstr)
            if (resp.isError) {
                FileLog.w("userLogin.do 返回错误: code=${resp.errorCode} msg=${resp.errorMessage}")
                handleLoginError(resp.errorCode, resp.errorMessage)
            } else {
                resp.data?.let {
                    FileLog.i("登录成功 memberID=${it.memberID}")
                    AccountManager.saveSession(
                        token = it.token.orEmpty(),
                        sid = it.sid.orEmpty(),
                        memberId = it.memberID,
                        phone = phone
                    )
                    _loginResult.value = it
                } ?: FileLog.w("userLogin.do 成功但 data 为空")
            }
        }
    }

    /** 处理登录错误码(对应 login.js switch(e.errorCode)) */
    private fun handleLoginError(code: String?, msg: String?) {
        when (code) {
            Constants.ErrorCode.NEED_IMG_CODE -> {
                // -8001021: 弹图形验证码, captchaType 切 2
                _needImgCode.value = true
                _toast.value = "需要图形验证码"
            }
            Constants.ErrorCode.NEED_ACTIVATE_PHONE -> {
                // -8001025: 需激活手机号, errorMessage 含 "|" 分隔的附加信息
                _toast.value = msg ?: "需要激活手机号"
            }
            Constants.ErrorCode.NOT_LOGIN -> _toast.value = "登录态失效,请重试"
            else -> _toast.value = msg ?: "登录失败($code)"
        }
    }

    /**
     * 密码加密
     *
     * 原 App 的加密算法在加密 dex 内,无法静态还原。
     * 真实实现需脱壳后从 com.zhenai 业务包提取 encryptPassword。
     * 这里用 SHA-256 摘要做占位,保证流程可编译运行。
     */
    private fun encryptPassword(pwd: String): String {
        val md = java.security.MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(pwd.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
