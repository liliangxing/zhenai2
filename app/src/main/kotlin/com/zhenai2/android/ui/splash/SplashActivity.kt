package com.zhenai2.android.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.zhenai2.common.AccountManager
import com.zhenai2.common.FileLog
import com.zhenai2.common.router.RouterPath
import com.zhenai2.network.NetworkClient
import kotlinx.coroutines.launch

/**
 * 启动页 —— 原 App 入口
 *
 * 原 App 在 AndroidManifest 注册为 LAUNCHER:
 *   com.zhenai2.android.ui.splash.SplashActivity (复刻后包名)
 *
 * 职责(对应原 App 启动流程):
 *   1. 调用 /api/system/appConfig.do 获取全局配置
 *   2. 调用 /api/login/checkLogin.do 检查登录态
 *   3. 已登录 -> 主页; 未登录 -> 登录页
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 原 App 启动有闪屏图,这里直接走逻辑,UI 由 res 迁移的资源承载
        try {
            lifecycleScope.launch {
                checkLoginAndRoute()
            }
        } catch (e: Throwable) {
            FileLog.e("SplashActivity 启动协程失败", e)
            routeToLogin()
        }
    }

    private suspend fun checkLoginAndRoute() {
        // 等待设备指纹采集完成,确保 checkLogin.do 请求携带 data 参数
        FileLog.i("等待设备指纹采集完成...")
        NetworkClient.awaitFingerprint()
        FileLog.i("设备指纹采集完成, 开始检查登录态")

        if (AccountManager.isLogin) {
            routeToMain()
            return
        }
        try {
            val resp = NetworkClient.apiService.checkLogin()
            if (resp.data?.isLogin == true) {
                routeToMain()
            } else {
                routeToLogin()
            }
        } catch (e: Exception) {
            FileLog.w("checkLogin.do 网络异常, 默认进登录页", e)
            routeToLogin()
        }
    }

    private fun routeToMain() {
        FileLog.i("SplashActivity -> 主页")
        ARouter.getInstance().build(RouterPath.MAIN).navigation(this)
        finish()
    }

    private fun routeToLogin() {
        FileLog.i("SplashActivity -> 登录页")
        ARouter.getInstance().build(RouterPath.LOGIN).navigation(this)
        finish()
    }
}
