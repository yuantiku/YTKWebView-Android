package com.fenbi.android.ytkwebview

import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @author zheng on 12/24/18
 */

open class CacheWebViewClient : WebViewClient() {

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        return YTKWebView.interceptRequest(url)
            ?: super.shouldInterceptRequest(view, url)
    }
}
