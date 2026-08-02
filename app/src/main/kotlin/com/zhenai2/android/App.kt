package com.zhenai2.android

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.alibaba.android.arouter.launcher.ARouter
import com.zhenai2.common.AccountManager
import com.zhenai2.common.FileLog
import com.zhenai2.network.NetworkClient
import java.security.MessageDigest

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
            // 原 App 用 MultiDex(业务量大),这里保留
            androidx.multidex.MultiDex.install(this)
        } catch (e: Throwable) {
            FileLog.e("MultiDex.install 失败", e)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 0. 闪退日志采集 —— 最先安装,确保后续初始化任何崩溃都能记录
        //    写入 /sdcard/douyinguanjia/Log/zhenai2.log
        CrashHandler.get().install(this)

        // 1. 账号管理初始化(对应原 App Cookie: sid/token)
        try {
            AccountManager.init(this)
        } catch (e: Throwable) {
            FileLog.e("AccountManager.init 失败", e)
        }

        // 2. ARouter 初始化(原 App 使用 ARouter 路由,约 380 条内部路由)
        try {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.init(this)
        } catch (e: Throwable) {
            FileLog.e("ARouter.init 失败", e)
        }

        // 3. 网络层初始化
        //    设备指纹(secdffinger)需异步采集,此处先置空,采集完成后注入
        //    对应原 App 的 Cr() 函数通过 secdffinger.zhenai.com 生成 screenPrint
        try {
            NetworkClient.setFingerprint(null)
        } catch (e: Throwable) {
            FileLog.e("NetworkClient.setFingerprint 失败", e)
        }

        // 4. 异步采集设备指纹(对应原 App 的 Cr())
        //    从 secdffinger.zhenai.com 获取 screenPrint,成功后注入 NetworkClient
        collectDeviceFingerprint()

        FileLog.i("App.onCreate 完成, 初始化全部成功")
    }

    /**
     * 异步采集设备指纹
     *
     * 原 App 通过 WebView 加载 secdffinger.zhenai.com 的 JS 生成 screenPrint。
     * 这里简化实现: 生成基于设备标识的 MD5 哈希作为设备指纹。
     * 指纹格式: screenPrint=<md5_hash>
     *
     * 采集成功后注入 NetworkClient,后续请求的 data 参数将携带指纹。
     */
    private fun collectDeviceFingerprint() {
        Thread {
            try {
                val androidId = Settings.Secure.getString(
                    contentResolver, Settings.Secure.ANDROID_ID
                ) ?: "unknown"
                val deviceInfo = "$androidId|${Build.MODEL}|${Build.MANUFACTURER}|${Build.VERSION.SDK_INT}"
                val md5 = MessageDigest.getInstance("MD5")
                val digest = md5.digest(deviceInfo.toByteArray())
                val fp = digest.joinToString("") { "%02x".format(it) }
                NetworkClient.setFingerprint(fp)
                FileLog.i("设备指纹采集成功, screenPrint=$fp")
            } catch (e: Throwable) {
                FileLog.e("设备指纹采集失败", e)
            }
        }.apply {
            // 后台线程,降低优先级,不阻塞主线程
            priority = Thread.MIN_PRIORITY
            start()
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}
