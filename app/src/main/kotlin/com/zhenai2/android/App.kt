package com.zhenai2.android

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.zhenai2.common.AccountManager
import com.zhenai2.common.FileLog
import com.zhenai2.network.NetworkClient

/**
 * Application 入口
 *
 * 复刻说明: 原 App 的 Application 是加固壳 s.h.e.l.l.S(爱加密),
 *          真实 Application 在加密 dex 内不可见。这里用标准 Application 重建,
 *          保留同等初始化职责: 账号、网络、路由。
 *
 * 原 App 在 AndroidManifest 中注册为:
 *   android:name="com.zhenai2.android.App" (复刻后包名)
 */
class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        try {
            androidx.multidex.MultiDex.install(this)
        } catch (e: Throwable) {
            FileLog.e("MultiDex.install 失败", e)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 0. 闪退日志采集
        CrashHandler.get().install(this)

        // 1. 账号管理初始化
        try {
            AccountManager.init(this)
        } catch (e: Throwable) {
            FileLog.e("AccountManager.init 失败", e)
        }

        // 2. ARouter 初始化
        try {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.init(this)
        } catch (e: Throwable) {
            FileLog.e("ARouter.init 失败", e)
        }

        // 3. 网络层初始化(指纹由FingerprintCollector采集后注入,不在此处设置)
        //    NetworkClient.fingerprint 默认为null, RequestInterceptor只在非null时添加data参数

        // 4. 异步采集通盾设备指纹(对应原 App 的 Cr())
        //    WebView 加载 apjs.html,通盾 SDK 采集 Canvas/WebGL 指纹生成 token
        //    成功后注入 NetworkClient,后续请求 data 参数携带 screenPrint=<token>
        collectDeviceFingerprint()

        FileLog.i("App.onCreate 完成, 初始化全部成功")
    }

    /**
     * 异步采集通盾设备指纹
     *
     * 原 App 通过 WebView 加载 assets/apjs.html（通盾 SDK），
     * 采集设备指纹生成 screenPrint token，注入到 NetworkClient。
     */
    private fun collectDeviceFingerprint() {
        FingerprintCollector().collect(this) { fp ->
            if (fp.isNotEmpty()) {
                NetworkClient.setFingerprint(fp)
            } else {
                FileLog.w("通盾指纹为空, 不注入data参数")
                NetworkClient.markFingerprintDone()
            }
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}
