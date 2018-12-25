# YTKWebView Android

Adds resource cache loading capability to WebViews. Rather than implementing it
as a subclass of WebView, we implement it as a utility that can be easily added to WebViews.

# How to use

You can install the cache-loading supported WebViewClient with one call

```kotlin
  webView.initCacheWebViewClient(applicationContext, cacheDirectory)
```

However, it's impractical to directly set the WebViewClient most of the time.
You need two steps in this case:

1. set the application context and the cache directory(could be null).

```kotlin
  ...
  YTKWebView.initCacheDirectory(
      // the application context
      applicationContext,
      // the cache directory, if set to null, ${context.filesDir}/cache/ will be
      // used.
      getDownloadDir().absolutePath
  )
```

2. in your WebViewClient, override shouldInterceptRequest()

```kotlin
class MyWebViewClient : WebViewClient() {

    ...
    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse {
        if (url != null) {
            YtkWebView.interceptRequest(url)?.let { return it }
        }
        return super.shouldInterceptRequest(view, url)
    }
}
```

That's it.
