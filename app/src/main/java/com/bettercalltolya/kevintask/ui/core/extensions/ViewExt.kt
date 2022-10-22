package com.bettercalltolya.kevintask.ui.core.extensions

import android.os.SystemClock
import android.view.View

fun View.fadeOut(
    duration: Long = 200L,
    endAction: (() -> Unit)? = null
) {
    animate().cancel()
    if (visibility == View.GONE || alpha == 0f) return

    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            visibility = View.GONE
            endAction?.invoke()
        }
        .start()
}

fun View.fadeIn(
    duration: Long = 200L,
    endAction: (() -> Unit)? = null
) {
    animate().cancel()
    if (visibility == View.VISIBLE || alpha == 1f) return

    animate()
        .alpha(1f)
        .setDuration(duration)
        .withStartAction { visibility = View.VISIBLE }
        .withEndAction { endAction?.invoke() }
        .start()
}

fun View.setDebounceClickListener(debounceTime: Long = 400L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
