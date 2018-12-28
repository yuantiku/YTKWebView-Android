package com.fenbi.android.ytkwebview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

/**
 * @author zheng on 12/29/18
 */

class TestActivity : AppCompatActivity() {

    val webView by lazy { WebView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(webView)
    }
}
