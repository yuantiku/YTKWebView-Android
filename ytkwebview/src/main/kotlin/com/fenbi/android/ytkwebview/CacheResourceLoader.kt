package com.fenbi.android.ytkwebview

import java.io.InputStream

/**
 * @author zheng on 12/24/18
 */

interface CacheResourceLoader {

    fun getCachedResourceStream(url: String?): InputStream?
}

fun ((String?) -> InputStream?).asCacheResourceLoader(): CacheResourceLoader {
    return object : CacheResourceLoader {
        override fun getCachedResourceStream(url: String?): InputStream? =
            this@asCacheResourceLoader(url)
    }
}
