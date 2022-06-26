package com.vannv.train.newsfly.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Author: vannv8@fpt.com.vn
 * Date: 26/06/2022
 */

class CircleBorderUp : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private val paint = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 60F
        val oval = RectF(100F, 100F, (this.width / 2).toFloat(), (this.height / 2).toFloat())
        canvas.drawArc(oval, 46F, 88F, false, paint.apply {
            color = Color.BLACK
        })
        paint.color = Color.BLACK
        canvas.drawArc(oval, 136F, 88F, false, paint)
        paint.color = Color.BLACK
        canvas.drawArc(oval, 226F, 88F, false, paint)
        paint.color = Color.BLACK
        canvas.drawArc(oval, 316F, 88F, false, paint)
    }

    fun setColorBorder(color: Int) {
        paint.color = color
        invalidate()
    }

}