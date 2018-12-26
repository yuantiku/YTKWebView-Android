# YTKWebView Android

Adds resource cache loading capability to WebViews. It could speed up the web
view loading by intercepting the web requests to load local resources.

Rather than implementing it as a subclass of WebView, we implement it as a
utility that can be easily added to WebViews.

## How to use

You can install the cache-loading supported WebViewClient with one call

```kotlin
  YtkWebView.init(applicationContext)
      .setCacheDirectory(cacheDirectoryPath)
      .setupWebViewClient(webView)
```

where the `webView` is the reference to the WebView.

However, it's impractical to directly set the WebViewClient most of the time.
You need two steps in this case:

1. set the application context.

```kotlin
  ...
  YtkWebView.init(applicationContext)
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

## Customize CacheResourceLoader

By default, we use the `DefaultCacheResourceLoader` which loads local cached
resources from assets and files with default mapping rules, and the default
caching directory `${context.filesDir}/cache/` will be used.

If you want to customize the cache resource loader,

```kotlin
  YtkWebView.init(applicationContext)
      .setCacheLoader(cacheResourceLoader)
```

The `setCacheLoader` method accepts a user-defined `cacheResourceLoader` which
could be an instance of `CacheResourceLoader` or a lambda function
of type `(url: String?) -> InputStream?` as argument.

If you want to use the `DefaultCacheResourceLoader` and only want to set
the caching directory,

```kotlin
  YtkWebView.init(applicationContext)
      .setCacheDirectory(cacheDirectoryPath)
```

### Mapping rules

The local resources could be pre-downloaded in a caching directory, or packaged
in the apk's assets.

For example,

https://ytkwebview.com/file1.html => ${cacheDirectory}/ytkwebview.com/file1.html

https://ytkwebview.com/a/b/c.json => ${cacheDirectory}/ytkwebview.com/a/b/c.json

If the resources are packaged in the assets, put them in the directory named `cache` in the
assets.
