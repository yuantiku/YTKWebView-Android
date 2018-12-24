package com.fenbi.android.ytkwebview

import android.net.Uri

/**
 * @author zheng on 12/24/18
 */

object FileNameUtils {

    fun getFilePath(url: String): String {
        val uri = Uri.parse(url)
        return "${uri.host}/${uri.path}"
    }

    fun getExtension(url: String): String? {
        val slash = url.lastIndexOf('/')
        if (slash < 0) {
            return null
        }
        val name = url.substring(slash)
        val dot = name.lastIndexOf('.')
        return if (dot < 0) {
            null
        } else name.substring(dot)
    }
}
