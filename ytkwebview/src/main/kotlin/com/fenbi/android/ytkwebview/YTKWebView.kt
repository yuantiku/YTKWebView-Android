package com.fenbi.android.ytkwebview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import java.io.File
import java.io.InputStream

/**
 * @author zheng on 12/21/18
 */

object YTKWebView {

    private lateinit var loader: CacheResourceLoader
    var isInited: Boolean = false

    private val protocols = listOf("http://", "https://")

    private val String.isSupported: Boolean
        get() = protocols.any { this.startsWith(it) }

    fun initCacheDirectory(context: Context, directory: String? = null) {
        val cacheDir = if (directory != null) {
            File(directory)
        } else {
            File(context.filesDir, "cache")
        }
        loader = DefaultCacheResourceLoader(context, cacheDir)
        isInited = true
    }

    fun interceptRequest(url: String?): WebResourceResponse? {
        return if (url != null && url.isSupported) {
            loader.getCachedResourceResponse(url)
        } else {
            null
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun interceptRequest(request: WebResourceRequest?): WebResourceResponse? {
        if (request?.method == "GET") {
            val url = request.url.toString()
            if (url.isSupported) {
                loader.getCachedResourceResponse(url)?.let {
                    return it
                }
            }
        }
        return null
    }

    fun getCachedResourceStream(url: String?): InputStream? {
        if (url != null && url.isSupported) {
            return loader.getCachedResourceStream(url)
        }
        return null
    }
}

fun WebView.initCacheWebViewClient(context: Context, directory: String? = null) {
    if (!YTKWebView.isInited) {
        YTKWebView.initCacheDirectory(context, directory)
    }
    webViewClient = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        CacheWebViewClient21()
    } else {
        CacheWebViewClient()
    }
}
