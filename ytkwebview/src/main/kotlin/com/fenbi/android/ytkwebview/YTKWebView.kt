package com.fenbi.android.ytkwebview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.InputStream

/**
 * @author zheng on 12/21/18
 */

class YTKWebView(context: Context) {

    private val context: Context = context.applicationContext

    private var loader: CacheResourceLoader? = null
        get() {
            return field ?: DefaultCacheResourceLoader(context).also {
                field = it
            }
        }

    private val ytkWebViewClient: YTKWebViewClient by lazy { YTKWebViewClient(this) }

    fun attach(webView: WebView): YTKWebView {
        webView.webViewClient = ytkWebViewClient
        return this
    }

    fun setWebViewClient(webViewClient: WebViewClient): YTKWebView {
        ytkWebViewClient.innerClient = webViewClient
        return this
    }

    fun setCacheLoader(loader: CacheResourceLoader): YTKWebView {
        this.loader = loader
        return this
    }

    fun setCacheLoader(loader: (url: String?) -> InputStream?): YTKWebView {
        this.loader = loader.asCacheResourceLoader()
        return this
    }

    fun defaultCacheLoader(assetsDirectory: String? = null, cacheDirectory: String? = null): YTKWebView {
        loader = DefaultCacheResourceLoader(context, assetsDirectory, cacheDirectory)
        return this
    }

    fun interceptRequest(url: String?): WebResourceResponse? {
        return if (url != null) {
            loader?.getCachedResourceResponse(url)
        } else {
            null
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun interceptRequest(request: WebResourceRequest?): WebResourceResponse? {
        if (request?.method == "GET") {
            val url = request.url.toString()
            loader?.getCachedResourceResponse(url)?.let {
                return it
            }
        }
        return null
    }

    fun getCachedResourceStream(url: String?): InputStream? {
        if (url != null) {
            return loader?.getCachedResourceStream(url)
        }
        return null
    }

    fun getLifecycle(): YTKWebViewLifecycle {
        return ytkWebViewClient.ytkWebViewLifecycle
    }
}
