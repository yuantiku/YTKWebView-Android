package com.fenbi.android.ytkwebview

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView

/**
 * @author zheng on 12/24/18
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CacheWebViewClient21 : CacheWebViewClient() {

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return YTKWebView.interceptRequest(request)
            ?: super.shouldInterceptRequest(view, request)
    }
}
