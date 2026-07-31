package com.zhenai2.login

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zhenai2.common.FileLog
import com.zhenai2.common.router.RouterPath

/**
 * 登录页 —— 复刻珍爱网账号密码登录
 *
 * ARouter 路由: /login/login
 * 原 App 对应: com.zhenai.*.login.LoginActivity(加密 dex 内,不可见)
 *
 * UI 用代码构建保证可运行,实际项目可替换为迁移的 layout XML。
 */
@Route(path = RouterPath.LOGIN)
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var etPhone: EditText
    private lateinit var etPwd: EditText
    private lateinit var etImgCode: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileLog.i("LoginActivity onCreate")
        try {
            viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
            setContentView(buildUI())
            viewModel.loadConfig()
            observe()
        } catch (e: Throwable) {
            FileLog.e("LoginActivity onCreate 失败", e)
            throw e
        }
    }

    private fun buildUI(): View {
        val pad = 48
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(pad, pad * 3, pad, pad)

            addView(TextView(this@LoginActivity).apply {
                text = "珍爱网登录"
                textSize = 22f
                setPadding(0, 0, 0, pad)
            })

            etPhone = EditText(this@LoginActivity).apply {
                hint = "手机号"; inputType = InputType.TYPE_CLASS_PHONE
                layoutParams = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = pad / 2 }
            }
            addView(etPhone)

            etPwd = EditText(this@LoginActivity).apply {
                hint = "密码"; inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                layoutParams = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = pad / 2 }
            }
            addView(etPwd)

            etImgCode = EditText(this@LoginActivity).apply {
                hint = "图形验证码(如需要)"
                visibility = View.GONE
                layoutParams = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = pad / 2 }
            }
            addView(etImgCode)

            btnLogin = Button(this@LoginActivity).apply {
                text = "登录"
                layoutParams = LinearLayout.LayoutParams(-1, -2)
            }
            addView(btnLogin)
        }
    }

    private fun observe() {
        viewModel.needImgCode.observe(this) { need ->
            etImgCode.visibility = if (need) View.VISIBLE else View.GONE
        }
        viewModel.loginResult.observe(this) { result ->
            if (result != null) {
                FileLog.i("登录成功, 跳转主页")
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                ARouter.getInstance().build(RouterPath.MAIN).navigation(this)
                finish()
            }
        }
        viewModel.toast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener {
            viewModel.login(
                phone = etPhone.text.toString(),
                password = etPwd.text.toString(),
                imgCode = etImgCode.text.toString()
            )
        }
    }
}
