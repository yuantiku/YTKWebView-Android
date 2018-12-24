package com.fenbi.android.ytkwebview

import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * @author zheng on 12/24/18
 */

class FileResourceLoader(private val directory: File) : CacheResourceLoader {

    override fun getCachedResourceStream(url: String?): InputStream? {
        if (url == null) {
            return null
        }
        val path = FileNameUtils.getFilePath(url)
        return try {
            FileInputStream(File(directory, path))
        } catch (e: Throwable) {
            null
        }
    }
}
