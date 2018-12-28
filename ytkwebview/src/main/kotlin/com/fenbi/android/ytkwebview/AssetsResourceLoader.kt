package com.fenbi.android.ytkwebview

import android.content.Context
import java.io.InputStream

/**
 * @author zheng on 12/24/18
 */

class AssetsResourceLoader(
    private val context: Context,
    private val directory: String) : CacheResourceLoader {

    override fun getCachedResourceStream(url: String?): InputStream? {
        if (url == null) {
            return null
        }
        val path = FileNameUtils.getFilePath(url)
        return try {
            context.assets.open("$directory/$path")
        } catch (e: Throwable) {
            null
        }
    }
}
