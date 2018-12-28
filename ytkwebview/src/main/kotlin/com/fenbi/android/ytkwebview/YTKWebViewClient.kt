package com.fenbi.android.ytkwebview

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.view.KeyEvent
import android.webkit.*

/**
 * @author zheng on 12/28/18
 */
class YTKWebViewClient(private val ytkWebView: YTKWebView) : WebViewClient() {

    internal var innerClient: WebViewClient? = null

    private var state: LifecycleState = LifecycleState.Uninitialized
        set(value) {
            field = value
            ytkWebViewLifecycle.onStateChanged?.invoke(value)
        }

    internal val ytkWebViewLifecycle = object : YTKWebViewLifecycle {

        override val state: LifecycleState
            get() = this@YTKWebViewClient.state

        override var onStateChanged: ((LifecycleState) -> Unit)? = null
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        innerClient?.let { return it.shouldOverrideUrlLoading(view, url) }
        return super.shouldOverrideUrlLoading(view, url)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        innerClient?.let { return it.shouldOverrideUrlLoading(view, request) }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        state = LifecycleState.Loading
        innerClient?.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        state = LifecycleState.Initialized
        innerClient?.onPageFinished(view, url)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        innerClient?.onLoadResource(view, url)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        innerClient?.onPageCommitVisible(view, url)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        return ytkWebView.interceptRequest(url)
            ?: innerClient?.let { return it.shouldInterceptRequest(view, url) }
            ?: super.shouldInterceptRequest(view, url)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return ytkWebView.interceptRequest(request)
            ?: innerClient?.let { return it.shouldInterceptRequest(view, request) }
            ?: super.shouldInterceptRequest(view, request)
    }

    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg)
        innerClient?.onTooManyRedirects(view, cancelMsg, continueMsg)
    }

    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        state = LifecycleState.Failed
        innerClient?.onReceivedError(view, errorCode, description, failingUrl)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        state = LifecycleState.Failed
        innerClient?.onReceivedError(view, request, error)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        state = LifecycleState.Failed
        innerClient?.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        super.onFormResubmission(view, dontResend, resend)
        innerClient?.onFormResubmission(view, dontResend, resend)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
        innerClient?.doUpdateVisitedHistory(view, url, isReload)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        innerClient?.onReceivedSslError(view, handler, error)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        super.onReceivedClientCertRequest(view, request)
        innerClient?.onReceivedClientCertRequest(view, request)
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
        innerClient?.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        innerClient?.let { return it.shouldOverrideKeyEvent(view, event) }
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        super.onUnhandledKeyEvent(view, event)
        innerClient?.onUnhandledKeyEvent(view, event)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
        innerClient?.onScaleChanged(view, oldScale, newScale)
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        super.onReceivedLoginRequest(view, realm, account, args)
        innerClient?.onReceivedLoginRequest(view, realm, account, args)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        innerClient?.let { return it.onRenderProcessGone(view, detail) }
        return super.onRenderProcessGone(view, detail)
    }

    @TargetApi(Build.VERSION_CODES.O_MR1)
    override fun onSafeBrowsingHit(
        view: WebView?, request: WebResourceRequest?, threatType: Int, callback: SafeBrowsingResponse?) {
        super.onSafeBrowsingHit(view, request, threatType, callback)
        innerClient?.onSafeBrowsingHit(view, request, threatType, callback)
    }
}
