package com.fenbi.android.ytkwebview

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author zheng on 12/28/18
 */

@RunWith(AndroidJUnit4::class)
class LifecycleTest {

    @Rule
    @JvmField
    val rule1 = ActivityTestRule(TestActivity::class.java)

    @Test
    fun testLifecycle() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val activity = rule1.activity
        activity.runOnUiThread {
            val lifecycle = YTKWebView(appContext)
                .attach(activity.webView)
                .getLifecycle()
            assertEquals(LifecycleState.Uninitialized, lifecycle.state)
            lifecycle.onStateChanged = { state ->
                assertEquals(lifecycle.state, state)
                assertNotEquals(LifecycleState.Uninitialized, state)
            }
            activity.webView.loadUrl("http://www.yuanfudao.com")
        }
    }
}
