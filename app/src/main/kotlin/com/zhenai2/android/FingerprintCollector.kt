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
 * 关键: WebView 必须加载 https://www.zhenai.com/ 以确保 origin 一致,
 * 否则通盾 SDK 采集的指纹绑定了错误的 origin, 后端验证不通过返回 428。
 *
 * 流程:
 * 1. WebView 加载 https://www.zhenai.com/
 * 2. 页面加载完成后, 注入通盾 SDK 配置和脚本
 * 3. 通盾 SDK 从 secdfinger.zhenai.com 加载 fm.js, 采集指纹
 * 4. 指纹采集成功后调用 window.TDJSSDK.getinfo(token)
 * 5. JavascriptInterface 接收 token, 注入 NetworkClient
 */
class FingerprintCollector {

    @Volatile private var webView: WebView? = null
    @Volatile private var callback: ((String) -> Unit)? = null
    @Volatile private var done = false

    @JavascriptInterface
    fun getinfo(result: String) {
        if (done) return
        done = true
        FileLog.i("通盾指纹采集成功: ${if (result.length > 80) result.take(80) + "..." else result}")
        callback?.invoke(result)

        Handler(Looper.getMainLooper()).post {
            try { webView?.destroy() } catch (_: Throwable) {}
            webView = null
        }
    }

    fun collect(context: Context, cb: (String) -> Unit) {
        callback = cb
        Handler(Looper.getMainLooper()).post {
            try {
                val wv = WebView(context)
                wv.settings.javaScriptEnabled = true
                wv.settings.domStorageEnabled = true
                wv.settings.databaseEnabled = true
                wv.settings.allowFileAccess = true
                wv.settings.allowContentAccess = true
                wv.settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                wv.settings.userAgentString = "Mozilla/5.0 (Linux; Android ${android.os.Build.VERSION.RELEASE}; ${android.os.Build.MODEL}) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

                wv.addJavascriptInterface(this@FingerprintCollector, "TDJSSDK")

                wv.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        FileLog.i("WebView页面加载完成: $url, 开始注入通盾SDK")
                        // 页面加载完成后注入通盾SDK
                        val js = """
                            (function() {
                                window._fmOpt = {
                                    partner: "zhenai",
                                    appName: "pcPrint",
                                    token: "zhenai-" + new Date().getTime() + "-" + Math.random().toString(16).substr(2),
                                    fpHost: "https://secdfinger.zhenai.com",
                                    fmb: true,
                                    cub: true,
                                    timeout: 5000,
                                    success: function(result) {
                                        window.TDJSSDK.getinfo(result);
                                    }
                                };
                                var s = document.createElement("script");
                                s.type = "text/javascript";
                                s.async = true;
                                s.src = window._fmOpt.fpHost + "/static/fm.js?ver=0.1&t=" + ((new Date().getTime() / 3600000).toFixed(0));
                                document.head.appendChild(s);
                            })();
                        """.trimIndent()
                        view?.evaluateJavascript(js, null)
                    }

                    override fun onReceivedError(
                        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                    ) {
                        FileLog.e("WebView加载错误: ${error?.description}")
                    }
                }

                FileLog.i("开始加载 https://www.zhenai.com/ 采集通盾指纹")
                wv.loadUrl("https://www.zhenai.com/")
                webView = wv

                // 20秒超时保护
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!done) {
                        FileLog.w("通盾指纹采集超时(20s)")
                        done = true
                        cb("")
                        try { wv.destroy() } catch (_: Throwable) {}
                        webView = null
                    }
                }, 20000)

            } catch (e: Throwable) {
                FileLog.e("通盾指纹采集初始化失败", e)
                done = true
                cb("")
            }
        }
    }
}
