package com.vannv.train.newsfly.presentation.widget

import android.os.Handler
import android.os.Looper
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View

/**
 * Author: vannv8@fpt.com.vn
 * Date: 25/06/2022
 */
class RepeatListener(
    private val initialInterval: Int, private val normalInterval: Int,
    private val onHold: (View) -> Unit,
    private val onRemoveHold: (View) -> Unit,
) : OnTouchListener {
    private val handler = Handler(Looper.getMainLooper())

    private var touchedView: View? = null
    private val handlerRunnable: Runnable = object : Runnable {
        override fun run() {
            if (touchedView?.isEnabled == true) {
                handler.postDelayed(this, normalInterval.toLong())
                touchedView ?: return
                onHold(touchedView!!)
            } else {
                // if the view was disabled by the clickListener, remove the callback
                handler.removeCallbacks(this)
                touchedView?.isPressed = false
                touchedView = null
            }
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                handler.removeCallbacks(handlerRunnable)
                handler.postDelayed(handlerRunnable, initialInterval.toLong())
                touchedView = view
                touchedView?.isPressed = true
                onHold(view)
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                handler.removeCallbacks(handlerRunnable)
                touchedView?.isPressed = false
                touchedView = null
                onRemoveHold(view)
                return true
            }
        }
        return false
    }
}