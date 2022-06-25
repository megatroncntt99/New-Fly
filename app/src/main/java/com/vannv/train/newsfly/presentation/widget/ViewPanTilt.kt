package com.vannv.train.newsfly.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.vannv.train.newsfly.R
import kotlin.math.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 22/06/2022
 */

class ViewPanTilt : RelativeLayout {
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_pan_tilt, this, true)
    private val cardView by lazy { view.findViewById<CardView>(R.id.card_view) }
    private val viewUp by lazy { view.findViewById<ImageView>(R.id.view_up) }
    private val viewDown by lazy { view.findViewById<ImageView>(R.id.view_down) }
    private val viewLeft by lazy { view.findViewById<ImageView>(R.id.view_left) }
    private val viewRight by lazy { view.findViewById<ImageView>(R.id.view_right) }

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

    private fun setEnableColorUp() {
        draggableListener?.moveCameraUp()
        viewUp.setBackgroundResource(R.color.white)
        viewDown.setBackgroundResource(R.color.black)
        viewLeft.setBackgroundResource(R.color.black)
        viewRight.setBackgroundResource(R.color.black)
    }

    private fun setEnableColorDown() {
        draggableListener?.moveCameraDown()
        viewUp.setBackgroundResource(R.color.black)
        viewDown.setBackgroundResource(R.color.white)
        viewLeft.setBackgroundResource(R.color.black)
        viewRight.setBackgroundResource(R.color.black)
    }


    private fun setEnableColorLeft() {
        draggableListener?.moveCameraLeft()
        viewUp.setBackgroundResource(R.color.black)
        viewDown.setBackgroundResource(R.color.black)
        viewLeft.setBackgroundResource(R.color.white)
        viewRight.setBackgroundResource(R.color.black)
    }

    private fun setEnableColorRight() {
        draggableListener?.moveCameraRight()
        viewUp.setBackgroundResource(R.color.black)
        viewDown.setBackgroundResource(R.color.black)
        viewLeft.setBackgroundResource(R.color.black)
        viewRight.setBackgroundResource(R.color.white)
    }

    private fun setDisableAllView() {
        draggableListener?.noneMoveCamera()
        viewUp.setBackgroundResource(R.color.black)
        viewDown.setBackgroundResource(R.color.black)
        viewLeft.setBackgroundResource(R.color.black)
        viewRight.setBackgroundResource(R.color.black)
    }

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
            val radius = calculateDistance(
                Point(xCenter.toDouble(), yCenter.toDouble()),
                Point(xMax.toDouble(), xMax.toDouble() / 2)
            )

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    widgetDX = v.x - event.rawX
                    widgetDY = v.y - event.rawY
                    widgetInitialX = v.x
                    widgetInitialY = v.y
                }
                MotionEvent.ACTION_MOVE -> {
                    var newX = event.rawX + widgetDX
                    newX = kotlin.math.max(0F, newX)
                    newX = kotlin.math.min(xMax.toFloat(), newX)


                    var newY = event.rawY + widgetDY
                    newY = kotlin.math.max(0F, newY)
                    newY = kotlin.math.min(yMax.toFloat(), newY)

                    val distanceBetweenCenterAndTouch: Float =
                        calculateDistance(
                            Point(xCenter.toDouble(), yCenter.toDouble()),
                            Point(newX.toDouble(), newY.toDouble())
                        )
                    if (distanceBetweenCenterAndTouch > radius) {
                        val circleLineIntersectionPoint = getIntersectionPoints(
                            Point(newX.toDouble(), newY.toDouble()),
                            Point(xCenter.toDouble(), yCenter.toDouble()),
                            radius.toDouble()
                        )
                        newX = circleLineIntersectionPoint.floatX
                        newY = circleLineIntersectionPoint.floatY
                    }
                    v.x = newX
                    v.y = newY
                    if (newX >= xMax - radius / 2) {
                        setEnableColorRight()
                    } else if (newX <= 0F + radius / 2) {
                        setEnableColorLeft()
                    } else if (newY >= yMax - radius / 2) {
                        setEnableColorDown()
                    } else if (newY <= 0F + radius / 2) {
                        setEnableColorUp()
                    } else {
                        setDisableAllView()
                    }

                }
                MotionEvent.ACTION_UP -> {
                    v.animate().x(xCenter.toFloat())
                        .y(yCenter.toFloat())
                        .setDuration(Draggable.DURATION_MILLIS)
                        .start()
                    draggableListener?.noneMoveCamera()
                    setDisableAllView()
                }
                else -> return@setOnTouchListener false
            }
            true
        }

        viewUp.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorUp()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )
        viewLeft.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorLeft()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )
        viewRight.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorRight()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )

        viewDown.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorDown()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )
    }


    fun setListener(draggableListener: DraggableListener?) {
        this.draggableListener = draggableListener
    }

    private fun getIntersectionPoints(touchPoint: Point, center: Point, radius: Double): Point {
        val angle = atan2(touchPoint.y - center.y, touchPoint.x - center.x)
        return Point(center.x + radius * cos(angle), center.y + radius * sin(angle))
    }

    private fun calculateDistance(a: Point, b: Point): Float {
        val squareDifference = (a.x - b.x).pow(2.0).toFloat()
        val squareDifference2 = (a.y - b.y).pow(2.0).toFloat()
        return sqrt((squareDifference + squareDifference2).toDouble()).toFloat()
    }
}

private object Draggable {
    const val DURATION_MILLIS = 250L
}

interface DraggableListener {
    fun noneMoveCamera()
    fun moveCameraLeft()
    fun moveCameraRight()
    fun moveCameraUp()
    fun moveCameraDown()

}

class Point(val x: Double, val y: Double) {
    val floatX: Float
        get() = x.toFloat()
    val floatY: Float
        get() = y.toFloat()

    override fun toString(): String {
        return "Point [x=$x, y=$y]"
    }
}
