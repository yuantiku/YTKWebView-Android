package com.fenbi.android.ytkwebview

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author zheng on 12/25/18
 */

@RunWith(AndroidJUnit4::class)
class Test1 {

    @Test
    fun testFileNameUtils() {
        assertEquals(FileNameUtils.getFilePath("https://ytkwebview.com/abc/d"), "ytkwebview.com/abc/d")
        assertEquals(FileNameUtils.getExtension("https://ytkwebview.com/test.html"), "html")
    }

    @Test
    fun test1() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val ytkWebView = YTKWebView(appContext)
        val response = ytkWebView.interceptRequest("https://ytkwebview.com/notexist.html")
        assertNull(response)
    }
}
