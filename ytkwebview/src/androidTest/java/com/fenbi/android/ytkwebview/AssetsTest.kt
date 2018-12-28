package com.fenbi.android.ytkwebview

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
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
        val appContext = InstrumentationRegistry.getTargetContext()
        val ytkWebView = YTKWebView(appContext)
        val response = ytkWebView.interceptRequest("https://ytkwebview.com/test.json")
        assertEquals(response!!.mimeType, "application/json")
        response.data.reader().use {
            assertEquals("""{"a":"a"}""", it.readText())
        }
    }
}
