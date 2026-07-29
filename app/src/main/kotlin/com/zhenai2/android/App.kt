package com.zhenai2.android

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.zhenai2.common.AccountManager
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
        // 原 App 用 MultiDex(业务量大),这里保留
        androidx.multidex.MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 0. 闪退日志采集 —— 最先安装,确保后续初始化任何崩溃都能记录
        //    写入 /sdcard/douyinguanjia/Log/zhenai2.log
        CrashHandler.get().install(this)

        // 1. 账号管理初始化(对应原 App Cookie: sid/token)
        AccountManager.init(this)

        // 2. ARouter 初始化(原 App 使用 ARouter 路由,约 380 条内部路由)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)

        // 3. 网络层初始化
        //    设备指纹(secdffinger)需异步采集,此处先置空,采集完成后注入
        //    对应原 App 的 Cr() 函数通过 secdffinger.zhenai.com 生成 screenPrint
        NetworkClient.setFingerprint(null)
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}
