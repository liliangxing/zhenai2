package com.zhenai2.android

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhenai2.common.FileLog

/**
 * 通盾设备指纹采集器
 *
 * 原 App 通过 WebView 加载 assets/apjs.html（通盾 SDK），
 * 采集 Canvas/WebGL/屏幕等指纹生成 token，回传给原生层。
 *
 * 流程:
 * 1. WebView 加载 apjs.html
 * 2. 通盾 SDK 自动执行，采集设备指纹
 * 3. SDK 调用 _fmOpt.success(token)
 * 4. success 回调调用 window.TDJSSDK.getinfo(token)
 * 5. JavascriptInterface 接收 token，注入 NetworkClient
 *
 * 指纹格式: screenPrint=<token>
 */
class FingerprintCollector {

    @Volatile private var webView: WebView? = null
    @Volatile private var callback: ((String) -> Unit)? = null
    @Volatile private var done = false

    /**
     * 通盾 SDK 回调入口
     * 由 apjs.html 中的 _fmOpt.success -> window.TDJSSDK.getinfo(result) 调用
     */
    @JavascriptInterface
    fun getinfo(result: String) {
        if (done) return
        done = true
        FileLog.i("通盾指纹采集成功: ${if (result.length > 80) result.take(80) + "..." else result}")
        callback?.invoke("screenPrint=$result")

        // 清理 WebView（必须在主线程）
        Handler(Looper.getMainLooper()).post {
            try {
                webView?.destroy()
            } catch (_: Throwable) {}
            webView = null
        }
    }

    /**
     * 启动指纹采集
     * @param context Application context
     * @param cb 回调，参数为 "screenPrint=<token>" 或空串（失败时）
     */
    fun collect(context: Context, cb: (String) -> Unit) {
        callback = cb
        Handler(Looper.getMainLooper()).post {
            try {
                val wv = WebView(context)
                wv.settings.javaScriptEnabled = true
                wv.settings.domStorageEnabled = true
                wv.settings.databaseEnabled = true
                wv.settings.allowFileAccess = true

                // 注入 JS 接口: window.TDJSSDK.getinfo(result)
                wv.addJavascriptInterface(this@FingerprintCollector, "TDJSSDK")

                wv.webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                    ) {
                        FileLog.e("WebView加载错误: ${error?.description}")
                    }
                }

                wv.loadUrl("file:///android_asset/apjs.html")
                webView = wv
                FileLog.i("开始加载通盾指纹SDK (apjs.html)")

                // 15秒超时保护
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!done) {
                        FileLog.w("通盾指纹采集超时(15s)")
                        done = true
                        cb("")
                        try { wv.destroy() } catch (_: Throwable) {}
                        webView = null
                    }
                }, 15000)

            } catch (e: Throwable) {
                FileLog.e("通盾指纹采集初始化失败", e)
                done = true
                cb("")
            }
        }
    }
}
