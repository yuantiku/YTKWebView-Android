# YTKWebView Android

Adds resource cache loading capability to WebViews. It could speed up the web
view loading by intercepting the web requests to load local resources.

Rather than implementing it as a subclass of WebView, we implement it as a
utility that can be easily added to WebViews.

## Basic Usage

You can enable the cache-loading support for the WebView with one call

```kotlin
  YTKWebView(context)
      .attach(webView)
```

where the `webView` is the reference to the WebView.

## Customizations

Optionally, you can set the WebViewClient, cache loader etc. like this,

```kotlin
  YTKWebView(context)
      .setWebViewClient(webViewClient)
      .setCacheLoader(cacheLoader)
      .attach(webView)
```

**Note**: If you have your own WebViewClient, do not directly set it via the
WebView's `setWebViewClient` method. Use `YTKWebView.setWebViewClient`
instead.

### Customize CacheResourceLoader

By default, we use the `DefaultCacheResourceLoader` which loads local cached
resources from assets and files with default mapping rules, and the default
caching directory `${context.filesDir}/cache/` will be used.
If the resources are packaged in the assets, they are looked up in
the directory named `cache` in the assets by default.

If you want to customize the cache resource loader, use

```kotlin
  ytkWebView.setCacheLoader(cacheResourceLoader)
```

The `setCacheLoader` method accepts a user-defined `cacheResourceLoader` which
could be an instance of `CacheResourceLoader` or a lambda function
of type `(url: String?) -> InputStream?` as argument.

If you want to use the `DefaultCacheResourceLoader` and only want to set
the caching directories,

```kotlin
  ytkWebView.defaultCacheLoader(
      assetsDirectory = assetsDirectory,
      cacheDirectory = cacheDirectoryPath)
```

### Mapping rules

For example,

```
https://ytkwebview.com/file1.html => ${cacheDirectory}/ytkwebview.com/file1.html

https://ytkwebview.com/a/b/c.json => ${cacheDirectory}/ytkwebview.com/a/b/c.json
```

## Lifecycle

After the `attach`,

```kotlin
  ytkWebView.getLifeCycle()
```

returns a `YTKWebViewLifecycle` that emits lifecycle state changes of the WebView.

Currently the lifecycle states include `Unitialized`, `Loading`, `Initialized`,
`Failed`, `Finished`.
