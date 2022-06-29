package com.vannv.train.newsfly.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
    private val viewUp by lazy { view.findViewById<CircleBorderUp>(R.id.circle_border_up) }
    private val viewDown by lazy { view.findViewById<CircleBorderDown>(R.id.circle_border_down) }
    private val viewLeft by lazy { view.findViewById<CircleBorderLeft>(R.id.circle_border_left) }
    private val viewRight by lazy { view.findViewById<CircleBorderRight>(R.id.circle_border_right) }
    private val ivUp by lazy { view.findViewById<ImageView>(R.id.iv_up) }
    private val ivDown by lazy { view.findViewById<ImageView>(R.id.iv_down) }
    private val ivLeft by lazy { view.findViewById<ImageView>(R.id.iv_left) }
    private val ivRight by lazy { view.findViewById<ImageView>(R.id.iv_right) }

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
        viewUp.setColorBorder(Color.WHITE)
        viewDown.setColorBorder(Color.BLACK)
        viewLeft.setColorBorder(Color.BLACK)
        viewRight.setColorBorder(Color.BLACK)
    }

    private fun setEnableColorDown() {
        draggableListener?.moveCameraDown()
        viewUp.setColorBorder(Color.BLACK)
        viewDown.setColorBorder(Color.WHITE)
        viewLeft.setColorBorder(Color.BLACK)
        viewRight.setColorBorder(Color.BLACK)
    }


    private fun setEnableColorLeft() {
        draggableListener?.moveCameraLeft()
        viewUp.setColorBorder(Color.BLACK)
        viewDown.setColorBorder(Color.BLACK)
        viewLeft.setColorBorder(Color.WHITE)
        viewRight.setColorBorder(Color.BLACK)
    }

    private fun setEnableColorRight() {
        draggableListener?.moveCameraRight()
        viewUp.setColorBorder(Color.BLACK)
        viewDown.setColorBorder(Color.BLACK)
        viewLeft.setColorBorder(Color.BLACK)
        viewRight.setColorBorder(Color.WHITE)
    }

    private fun setDisableAllView() {
        draggableListener?.noneMoveCamera()
        viewUp.setColorBorder(Color.BLACK)
        viewDown.setColorBorder(Color.BLACK)
        viewLeft.setColorBorder(Color.BLACK)
        viewRight.setColorBorder(Color.BLACK)
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

        ivUp.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorUp()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )
        ivLeft.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorLeft()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )
        ivRight.setOnTouchListener(
            RepeatListener(400, 100,
                onHold = {
                    setEnableColorRight()

                },
                onRemoveHold = {
                    setDisableAllView()
                })
        )

        ivDown.setOnTouchListener(
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
