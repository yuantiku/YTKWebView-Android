package com.fenbi.android.ytkwebview

import android.content.Context
import java.io.File
import java.io.InputStream

/**
 * @author zheng on 12/24/18
 */

class DefaultCacheResourceLoader(
    private val context: Context,
    directory: String? = null) : CacheResourceLoader {

    private val cacheDir = if (directory != null) {
        File(directory)
    } else {
        File(context.filesDir, "cache")
    }

    private val innerLoaders by lazy {
        listOf(AssetsResourceLoader(context), FileResourceLoader(cacheDir))
    }

    override fun getCachedResourceStream(url: String?): InputStream? {
        innerLoaders.forEach { loader ->
            loader.getCachedResourceStream(url)?.let {
                return it
            }
        }
        return null
    }
}
