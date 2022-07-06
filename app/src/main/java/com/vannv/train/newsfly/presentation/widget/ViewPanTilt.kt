package com.vannv.train.newsfly.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.LogCat

/**
 * Author: vannv8@fpt.com.vn
 * Date: 22/06/2022
 */

class ViewPanTilt : RelativeLayout {
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_pan_tilt, this, true)
    private val viewUp by lazy { view.findViewById<CircleBorderUp>(R.id.circle_border_up) }
    private val viewDown by lazy { view.findViewById<CircleBorderDown>(R.id.circle_border_down) }
    private val viewLeft by lazy { view.findViewById<CircleBorderLeft>(R.id.circle_border_left) }
    private val viewRight by lazy { view.findViewById<CircleBorderRight>(R.id.circle_border_right) }
    private val joyStickView by lazy { view.findViewById<JoystickView>(R.id.joy_stick_view) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var moveCameraListener: MoveCameraListener? = null


    private fun setEnableColorUp() {
        moveCameraListener?.moveCameraUp()
        viewUp.setColorBorder(R.color.borderCircleActive)
        viewDown.setColorBorder(R.color.borderCircleInActive)
        viewLeft.setColorBorder(R.color.borderCircleInActive)
        viewRight.setColorBorder(R.color.borderCircleInActive)
    }

    private fun setEnableColorDown() {
        moveCameraListener?.moveCameraDown()
        viewUp.setColorBorder(R.color.borderCircleInActive)
        viewDown.setColorBorder(R.color.borderCircleActive)
        viewLeft.setColorBorder(R.color.borderCircleInActive)
        viewRight.setColorBorder(R.color.borderCircleInActive)
    }

    private fun setEnableColorLeft() {
        moveCameraListener?.moveCameraLeft()
        viewUp.setColorBorder(R.color.borderCircleInActive)
        viewDown.setColorBorder(R.color.borderCircleInActive)
        viewLeft.setColorBorder(R.color.borderCircleActive)
        viewRight.setColorBorder(R.color.borderCircleInActive)
    }

    private fun setEnableColorRight() {
        moveCameraListener?.moveCameraRight()
        viewUp.setColorBorder(R.color.borderCircleInActive)
        viewDown.setColorBorder(R.color.borderCircleInActive)
        viewLeft.setColorBorder(R.color.borderCircleInActive)
        viewRight.setColorBorder(R.color.borderCircleActive)
    }

    private fun setDisableAllView() {
        moveCameraListener?.noneMoveCamera()
        viewUp.setColorBorder(R.color.borderCircleInActive)
        viewDown.setColorBorder(R.color.borderCircleInActive)
        viewLeft.setColorBorder(R.color.borderCircleInActive)
        viewRight.setColorBorder(R.color.borderCircleInActive)
    }

    init {
        draggableSetup()
    }


    private fun draggableSetup() {
        joyStickView.setOnMoveListener(object : JoystickView.OnMoveListener {
            override fun onMove(angle: Int, strength: Int) {
                joyStickView.setActiveMove(R.color.moveCameraActive)
                if (angle == 0 && strength == 0) {
                    setDisableAllView()
                    joyStickView.setActiveMove(R.color.chipSelect)
                    return
                }
                if ((angle in 0..45 || angle in 315..360) && strength > 10) {
                    setEnableColorRight()
                    return
                }

                if (angle in 45..135 && strength > 10) {
                    setEnableColorUp()
                    return
                }

                if (angle in 135..225 && strength > 10) {
                    setEnableColorLeft()
                    return
                }

                if (angle in 225..315 && strength > 10) {
                    setEnableColorDown()
                    return
                }
            }
        })
    }


    fun setListener(moveCameraListener: MoveCameraListener) {
        this.moveCameraListener = moveCameraListener
    }

}

interface MoveCameraListener {
    fun noneMoveCamera()
    fun moveCameraLeft()
    fun moveCameraRight()
    fun moveCameraUp()
    fun moveCameraDown()
}