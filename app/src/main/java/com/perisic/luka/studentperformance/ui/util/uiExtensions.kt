package com.perisic.luka.studentperformance.ui.util

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

fun TextView.stringText(): String {
    return this.text?.toString() ?: ""
}

fun TextView.postDelayedWatcher(delay: Long = 800, callback: (String) -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    var text = ""
    val handlerCallback = Runnable {
        synchronized(text) {
            callback(text)
        }
    }

    addTextChangedListener {
        synchronized(text) {
            text = it.toString()
            handler.removeCallbacks(handlerCallback)
            handler.postDelayed(handlerCallback, delay)
        }
    }
}