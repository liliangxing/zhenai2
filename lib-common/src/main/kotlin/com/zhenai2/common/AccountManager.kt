package com.zhenai2.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 账号/会话管理
 *
 * 复刻原 App 的登录态管理:
 *   - token / sid / memberID 持久化(对应原 App 的 Cookie: sid、token)
 *   - 登录态判断(对应 /login/checkLogin.do 的 isLogin)
 *   - 退出登录清理
 */
object AccountManager {

    private const val SP = "za_account"
    private const val K_TOKEN = "token"
    private const val K_SID = "sid"
    private const val K_MEMBER_ID = "memberID"
    private const val K_PHONE = "phone"

    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        sp = context.applicationContext.getSharedPreferences(SP, Context.MODE_PRIVATE)
    }

    /** 登录成功后保存会话 */
    fun saveSession(token: String, sid: String, memberId: Long, phone: String) {
        sp.edit {
            putString(K_TOKEN, token)
            putString(K_SID, sid)
            putLong(K_MEMBER_ID, memberId)
            putString(K_PHONE, phone)
        }
    }

    var token: String?
        get() = sp.getString(K_TOKEN, null)
        set(v) = sp.edit { putString(K_TOKEN, v) }

    var sid: String?
        get() = sp.getString(K_SID, null)
        set(v) = sp.edit { putString(K_SID, v) }

    val memberId: Long
        get() = sp.getLong(K_MEMBER_ID, 0L)

    val phone: String?
        get() = sp.getString(K_PHONE, null)

    /** 是否已登录 */
    val isLogin: Boolean
        get() = !token.isNullOrEmpty() && memberId > 0L

    /** 退出登录 */
    fun logout() {
        sp.edit { remove(K_TOKEN).remove(K_MEMBER_ID) }
    }
}
