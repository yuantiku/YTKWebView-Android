package com.fenbi.android.ytkwebview

/**
 * @author zheng on 12/28/18
 */

interface YTKWebViewLifecycle {

    val state: LifecycleState

    var onStateChanged: ((LifecycleState) -> Unit)?
}
