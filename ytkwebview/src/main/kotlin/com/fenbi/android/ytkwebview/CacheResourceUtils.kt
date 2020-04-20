package com.fenbi.android.ytkwebview

import android.net.Uri
import android.os.Build
import android.webkit.WebResourceResponse

/**
 * @author zheng on 12/24/18
 */

fun CacheResourceLoader.getCachedResourceResponse(url: String): WebResourceResponse? {
    val inputStream = getCachedResourceStream(url)
    return if (inputStream != null) {
        val path = Uri.parse(url).path.orEmpty()
        val extension = FileNameUtils.getExtension(path)
        val mimeType = MimeTypes.getMimeType(extension)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val encoding = "UTF-8"
            val statusCode = 200
            val reasonPhase = "OK"
            val responseHeaders = mutableMapOf("Access-Control-Allow-Origin" to "*")
            WebResourceResponse(
                mimeType, encoding, statusCode, reasonPhase,
                responseHeaders, inputStream)
        } else {
            WebResourceResponse(mimeType, "UTF-8", inputStream)
        }
    } else {
        null
    }
}
