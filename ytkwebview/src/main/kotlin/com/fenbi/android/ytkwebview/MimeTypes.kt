package com.fenbi.android.ytkwebview

/**
 * @author zheng on 12/24/18
 */

object MimeTypes {

    fun getMimeType(extension: String?): String {
        return when (extension) {
            "htm", "html" -> "text/html"
            "css" -> "text/css"
            "js" -> "text/javascript"
            "png" -> "image/png"
            "jpg", "jpeg" -> "image/jpeg"
            "ico" -> "image/x-icon"
            "json" -> "application/json"
            "woff", "ttf", "eot" -> "application/x-font-opentype"
            "mp4" -> "video/mp4"
            else -> "application/octet-stream"
        }
    }
}
