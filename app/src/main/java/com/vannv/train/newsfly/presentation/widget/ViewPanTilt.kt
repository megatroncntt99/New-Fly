package com.vannv.train.newsfly.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.FloatMath
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.vannv.train.newsfly.R
import java.lang.Math.sqrt
import kotlin.math.atan2

/**
 * Author: vannv8@fpt.com.vn
 * Date: 22/06/2022
 */

class ViewPanTilt : RelativeLayout {
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_pan_tilt, this, true)
    private val cardView by lazy { view.findViewById<CardView>(R.id.card_view) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var draggableListener: DraggableListener? = null
    private var widgetInitialX: Float = 0F
    private var widgetDX: Float = 0F
    private var widgetInitialY: Float = 0F
    private var widgetDY: Float = 0F

    init {
        draggableSetup()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun draggableSetup() {
        cardView.setOnTouchListener { v, event ->
            val viewParent = v.parent as View
            val parentHeight = viewParent.height
            val parentWidth = viewParent.width
            val xMax = parentWidth - v.width
            val yMax = parentHeight - v.height
            val xCenter = xMax / 2
            val yCenter = yMax / 2

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    widgetDX = v.x - event.rawX
                    widgetDY = v.y - event.rawY
                    widgetInitialX = v.x
                    widgetInitialY = v.y
                }
                MotionEvent.ACTION_MOVE -> {
                    var newX = event.rawX + widgetDX
                    newX = kotlin.math.max(0F + Draggable.RADIUS, newX)
                    newX = kotlin.math.min(xMax.toFloat() - Draggable.RADIUS, newX)


                    var newY = event.rawY + widgetDY
                    newY = kotlin.math.max(0F + Draggable.RADIUS, newY)
                    newY = kotlin.math.min(yMax.toFloat() - Draggable.RADIUS, newY)

                    v.x = newX
                    v.y = newY
                    if (newX >= xMax - Draggable.RADIUS) {
                        draggableListener?.moveCameraRight()
                    } else if (newX == 0F + Draggable.RADIUS) {
                        draggableListener?.moveCameraLeft()

                    } else if (newY >= yMax - Draggable.RADIUS) {
                        draggableListener?.moveCameraDown()
                    } else if (newY == 0F + Draggable.RADIUS) {
                        draggableListener?.moveCameraUp()
                    }

                }
                MotionEvent.ACTION_UP -> {
                    v.animate().x(xCenter.toFloat())
                        .y(yCenter.toFloat())
                        .setDuration(Draggable.DURATION_MILLIS)
                        .start()
                    draggableListener?.moveNone()
                }
                else -> return@setOnTouchListener false
            }
            true
        }
    }


    fun setListener(draggableListener: DraggableListener?) {
        this.draggableListener = draggableListener
    }

}

private object Draggable {
    const val DRAG_TOLERANCE = 16
    const val DURATION_MILLIS = 250L
    const val RADIUS = 80
}

interface DraggableListener {
    fun moveNone()
    fun moveCameraLeft()
    fun moveCameraRight()
    fun moveCameraUp()
    fun moveCameraDown()

}
