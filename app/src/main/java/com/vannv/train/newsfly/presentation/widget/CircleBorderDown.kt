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
 * Date: 27/06/2022
 */
class CircleBorderDown : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val borderWith: Float = 80F

    init {
    }

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = borderWith
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val width = width - borderWith
        val height = height - borderWith
        val left = (getWidth() - width) / 2.0f
        val top = (getHeight() - height) / 2.0f
        val oval = RectF(left, top, left + width, top + height)
        canvas.drawArc(oval, 46F, 88F, false, paint)
    }

    fun setColorBorder(color: Int) {
        paint.color = color
        invalidate()
    }

}