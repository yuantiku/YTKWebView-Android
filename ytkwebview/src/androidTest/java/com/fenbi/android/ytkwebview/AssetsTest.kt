package com.fenbi.android.ytkwebview

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author zheng on 12/26/18
 */

@RunWith(AndroidJUnit4::class)
class AssetsTest {

    @Test
    fun testAssets() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val ytkWebView = YTKWebView(appContext)
        val response = ytkWebView.interceptRequest("https://ytkwebview.com/test.json")
        assertEquals(response!!.mimeType, "application/json")
        response.data.reader().use {
            assertEquals("""{"a":"a"}""", it.readText())
        }
    }
}
