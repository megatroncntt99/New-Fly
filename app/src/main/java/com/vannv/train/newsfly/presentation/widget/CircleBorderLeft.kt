package com.vannv.train.newsfly.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.vannv.train.newsfly.R

/**
 * Author: vannv8@fpt.com.vn
 * Date: 27/06/2022
 */
class CircleBorderLeft : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val borderWith: Float = 80F


    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.borderCircleInActive)
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
        canvas.drawArc(oval, 136F, 88F, false, paint)
    }

    fun setColorBorder(color: Int) {
        paint.color = ContextCompat.getColor(context, color)
        invalidate()
    }

}