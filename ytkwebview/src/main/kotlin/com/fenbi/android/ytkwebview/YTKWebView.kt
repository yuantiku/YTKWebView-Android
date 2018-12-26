package com.fenbi.android.ytkwebview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import java.io.InputStream

/**
 * @author zheng on 12/21/18
 */

@Suppress("StaticFieldLeak")
object YTKWebView {

    private lateinit var context: Context

    private var loader: CacheResourceLoader? = null
        get() {
            return field ?: DefaultCacheResourceLoader(context).also {
                field = it
            }
        }

    private val protocols = listOf("http://", "https://")

    private val String.isSupported: Boolean
        get() = protocols.any { this.startsWith(it) }

    fun init(context: Context): YTKWebView {
        this.context = context.applicationContext
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

    fun setCacheDirectory(directory: String? = null): YTKWebView {
        loader = DefaultCacheResourceLoader(context, directory)
        return this
    }

    fun setupWebViewClient(webView: WebView) {
        webView.webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CacheWebViewClient21()
        } else {
            CacheWebViewClient()
        }
    }

    fun interceptRequest(url: String?): WebResourceResponse? {
        return if (url != null && url.isSupported) {
            loader?.getCachedResourceResponse(url)
        } else {
            null
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun interceptRequest(request: WebResourceRequest?): WebResourceResponse? {
        if (request?.method == "GET") {
            val url = request.url.toString()
            if (url.isSupported) {
                loader?.getCachedResourceResponse(url)?.let {
                    return it
                }
            }
        }
        return null
    }

    fun getCachedResourceStream(url: String?): InputStream? {
        if (url != null && url.isSupported) {
            return loader?.getCachedResourceStream(url)
        }
        return null
    }
}
