package com.fenbi.android.ytkwebview

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream

/**
 * @author zheng on 12/26/18
 */

@RunWith(AndroidJUnit4::class)
class FileTest {

    private val testUrl = "https://ytkwebview.com/file1.html"
    private val fileContent = "<html><body>ha</body></html>"
    private lateinit var ytkWebView: YTKWebView

    @Before
    fun prepare() {
        val appContext = InstrumentationRegistry.getTargetContext()
        ytkWebView = YTKWebView(appContext)

        val dir = File(appContext.filesDir, "cache/ytkwebview.com")
        dir.mkdirs()
        val file = File(dir, "file1.html")
        FileOutputStream(file).writer().use {
            it.write(fileContent)
        }
    }

    @Test
    fun testFile() {
        val response = ytkWebView.interceptRequest(testUrl)
        assertEquals(response!!.mimeType, "text/html")
        response.data.reader().use {
            assertEquals(fileContent, it.readText())
        }
    }
}
