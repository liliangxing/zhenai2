package com.zhenai2.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.Constants
import com.zhenai2.common.router.RouterPath

/**
 * H5 容器 Fragment
 *
 * ARouter 路由: /web/html
 * 职责: 承载 i.zhenai.com 内嵌 H5 页面。原包 com.zhenai.common.web.h5.*
 *   - BaseHtmlActivity / BaseWebkitActivity
 *
 * 原 App 大量业务页以 H5 形式承载(i.zhenai.com/m/sapp/...),
 * WebView 与 native 通过 JSBridge 交互(对应 assets/apjs.html 的 jsbridge)。
 */
@Route(path = RouterPath.WEB_HTML)
class WebFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val webView = WebView(requireContext()).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            // 默认加载珍爱 H5 首页,实际使用时通过 arguments 传 url
            val url = arguments?.getString("url") ?: Constants.H5_HOST
            loadUrl(url)
        }
        return webView
    }
}
