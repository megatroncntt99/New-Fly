package com.vannv.train.newsfly.presentation.widget

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.ContextCompat
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.LogCat
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Author: vannv8@fpt.com.vn
 * Date: 29/06/2022
 */
class JoystickView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs), Runnable {


    interface OnMoveListener {
        fun onMove(angle: Int, strength: Int)
    }


    interface OnMultipleLongPressListener {
        fun onMultipleLongPress()
    }

    // DRAWING
    private val mPaintCircleButton: Paint
    private val mPaintCircleBorder: Paint
    private val mPaintBackground: Paint
    private var mPaintBitmapButton: Paint? = null


    private var mButtonSizeRatio = 0f

    private var mBackgroundSizeRatio = 0f

    // COORDINATE
    private var mPosX = 0
    private var mPosY = 0
    private var mCenterX = 0
    private var mCenterY = 0
    private var mFixedCenterX = 0
    private var mFixedCenterY = 0


    private var mFixedCenter = false

    private var isAutoReCenterButton = false

    private var isButtonStickToBorder = false


    private var mEnabled = false

    // SIZE
    private var mButtonRadius = 0
    private var mBorderRadius = 0
    private var mBorderAlpha = 0
    private var mBackgroundRadius = 0f

    private var mCallback: OnMoveListener? = null
    private var mLoopInterval = DEFAULT_LOOP_INTERVAL.toLong()
    private var mThread = Thread(this)


    private var mOnMultipleLongPressListener: OnMultipleLongPressListener? = null
    private val mHandlerMultipleLongPress = Handler(Looper.getMainLooper())
    private val mRunnableMultipleLongPress: Runnable
    private var mMoveTolerance = 0
    private var buttonDirection = 0
    private var isDrag: Boolean = false

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs) {}

    private fun initPosition() {
        // get the center of view to position circle
        mPosX = width / 2
        mCenterX = mPosX
        mFixedCenterX = mCenterX
        mPosY = width / 2
        mCenterY = mPosY
        mFixedCenterY = mCenterY
    }

    fun setActiveMove(color: Int) {
        isEnabled = true
        mPaintCircleButton.color = ContextCompat.getColor(context, color)
    }

    fun setDisableMove(color: Int) {
        isEnabled = false
        mPaintCircleButton.color = ContextCompat.getColor(context, color)
    }

    override fun onDraw(canvas: Canvas) {
        // Draw the background
        canvas.drawCircle(mFixedCenterX.toFloat(), mFixedCenterY.toFloat(), mBackgroundRadius, mPaintBackground)

        // Draw the circle border
        canvas.drawCircle(mFixedCenterX.toFloat(), mFixedCenterY.toFloat(), mBorderRadius.toFloat(), mPaintCircleBorder)

        // Draw the button from image

        canvas.drawCircle(
            (
                    mPosX + mFixedCenterX - mCenterX).toFloat(), (
                    mPosY + mFixedCenterY - mCenterY).toFloat(),
            mButtonRadius.toFloat(),
            mPaintCircleButton
        )

    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        initPosition()

        val d = w.coerceAtMost(h)
        mButtonRadius = (d / 2 * mButtonSizeRatio).toInt()
        mBorderRadius = (d / 2 * mBackgroundSizeRatio).toInt()
        mBackgroundRadius = mBorderRadius - mPaintCircleBorder.strokeWidth / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = measure(widthMeasureSpec).coerceAtMost(measure(heightMeasureSpec))
        setMeasuredDimension(d, d)
    }

    private fun measure(measureSpec: Int): Int {
        return if (MeasureSpec.getMode(measureSpec) == MeasureSpec.UNSPECIFIED) {
            DEFAULT_SIZE
        } else {
            MeasureSpec.getSize(measureSpec)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // if disabled we don't move the
        if (!mEnabled) {
            return true
        }
        if (isDrag && event.action != MotionEvent.ACTION_UP)
            return false
        mPosY = if (buttonDirection < 0) mCenterY else event.y.toInt() // direction negative is horizontal axe
        mPosX = if (buttonDirection > 0) mCenterX else event.x.toInt() // direction positive is vertical axe
        if (event.action == MotionEvent.ACTION_UP) {
            isDrag = false
            mThread.interrupt()

            if (isAutoReCenterButton) {
                resetButtonPosition()

                mCallback?.onMove(angle, strength)
            }
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            if (mThread.isAlive) {
                mThread.interrupt()
            }
            mThread = Thread(this)
            mThread.start()
            mCallback?.onMove(angle, strength)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN ->
                if (!mFixedCenter) {
                    mCenterX = mPosX
                    mCenterY = mPosY
                }
            MotionEvent.ACTION_POINTER_DOWN -> {

                // when the second finger touch
                if (event.pointerCount == 2) {
                    mHandlerMultipleLongPress.postDelayed(mRunnableMultipleLongPress, (ViewConfiguration.getLongPressTimeout() * 2).toLong())
                    mMoveTolerance = MOVE_TOLERANCE
                }
            }
            MotionEvent.ACTION_MOVE -> {
                mMoveTolerance--
                if (mMoveTolerance == 0) {
                    mHandlerMultipleLongPress.removeCallbacks(mRunnableMultipleLongPress)
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {

                // when the last multiple touch is released
                if (event.pointerCount == 2) {
                    mHandlerMultipleLongPress.removeCallbacks(mRunnableMultipleLongPress)
                }
            }
        }
        val abs = sqrt(
            ((mPosX - mCenterX) * (mPosX - mCenterX)
                    + (mPosY - mCenterY) * (mPosY - mCenterY)).toDouble()
        )

        // (abs > mBorderRadius) means button is too far therefore we limit to border
        // (buttonStickBorder && abs != 0) means wherever is the button we stick it to the border except when abs == 0

        if (abs > mBorderRadius || isButtonStickToBorder && abs != 0.0) {
            mPosX = ((mPosX - mCenterX) * mBorderRadius / abs + mCenterX).toInt()
            mPosY = ((mPosY - mCenterY) * mBorderRadius / abs + mCenterY).toInt()
        }
        if (!isAutoReCenterButton) {
            // Now update the last strength and angle if not reset to center
            mCallback?.onMove(angle, strength)
        }
        if (abs > 2.5 * mBorderRadius) {
            resetButtonPosition()
            mCallback?.onMove(angle, strength)
            isDrag = true
        }


        // to force a new draw
        invalidate()

        return true
    }
    /*
    GETTERS
     */// make it as a regular counter-clock protractor
    /**
     * Process the angle following the 360° counter-clock protractor rules.
     * @return the angle of the button
     */
    private val angle: Int
        private get() {
            val angle = Math.toDegrees(atan2((mCenterY - mPosY).toDouble(), (mPosX - mCenterX).toDouble())).toInt()
            return if (angle < 0) angle + 360 else angle // make it as a regular counter-clock protractor
        }

    /**
     * Process the strength as a percentage of the distance between the center and the border.
     * @return the strength of the button
     */
    private val strength: Int
        private get() = (100 * sqrt(
            ((mPosX - mCenterX)
                    * (mPosX - mCenterX) + (mPosY - mCenterY)
                    * (mPosY - mCenterY)).toDouble()
        ) / mBorderRadius).toInt()

    private fun resetButtonPosition() {
        mPosX = mCenterX
        mPosY = mCenterY
    }

    override fun isEnabled(): Boolean {
        return mEnabled
    }

    var buttonSizeRatio: Float
        get() = mButtonSizeRatio
        set(newRatio) {
            if (newRatio > 0.0f && newRatio <= 1.0f) {
                mButtonSizeRatio = newRatio
            }
        }

    fun getmBackgroundSizeRatio(): Float {
        return mBackgroundSizeRatio
    }

    val normalizedX: Int
        get() = if (width == 0) {
            50
        } else ((mPosX - mButtonRadius) * 100.0f / (width - mButtonRadius * 2)).roundToInt()
    val normalizedY: Int
        get() = if (height == 0) {
            50
        } else ((mPosY - mButtonRadius) * 100.0f / (height - mButtonRadius * 2)).roundToInt()

    var borderAlpha: Int
        get() = mBorderAlpha
        set(alpha) {
            mBorderAlpha = alpha
            mPaintCircleBorder.alpha = alpha
            invalidate()
        }
    /*
    SETTERS
     */
    /**
     * Set an image to the button with a drawable
     * @param d drawable to pick the image
     */
    fun setButtonDrawable(d: Drawable?) {
//        if (d != null) {
//            if (d is BitmapDrawable) {
//                mButtonBitmap = d.bitmap
//                if (mButtonRadius != 0) {
//                    mButtonBitmap = Bitmap.createScaledBitmap(
//                        mButtonBitmap as Bitmap,
//                        mButtonRadius * 2,
//                        mButtonRadius * 2,
//                        true
//                    )
//                }
//                if (mPaintBitmapButton != null) mPaintBitmapButton = Paint()
//            }
//        }
    }

    /**
     * Set the button color for this JoystickView.
     * @param color the color of the button
     */
    fun setButtonColor(color: Int) {
        mPaintCircleButton.color = color
        invalidate()
    }

    /**
     * Set the border color for this JoystickView.
     * @param color the color of the border
     */
    fun setBorderColor(color: Int) {
        mPaintCircleBorder.color = color
        if (color != Color.TRANSPARENT) {
            mPaintCircleBorder.alpha = mBorderAlpha
        }
        invalidate()
    }

    /**
     * Set the background color for this JoystickView.
     * @param color the color of the background
     */
    override fun setBackgroundColor(color: Int) {
        mPaintBackground.color = color
        invalidate()
    }

    /**
     * Set the border width for this JoystickView.
     * @param width the width of the border
     */
    fun setBorderWidth(width: Int) {
        mPaintCircleBorder.strokeWidth = width.toFloat()
        mBackgroundRadius = mBorderRadius - width / 2.0f
        invalidate()
    }

    /**
     * Register a callback to be invoked when this JoystickView's button is moved
     * @param l The callback that will run
     */
    fun setOnMoveListener(l: OnMoveListener?) {
        setOnMoveListener(l, DEFAULT_LOOP_INTERVAL)
    }

    /**
     * Register a callback to be invoked when this JoystickView's button is moved
     * @param l The callback that will run
     * @param loopInterval Refresh rate to be invoked in milliseconds
     */
    fun setOnMoveListener(l: OnMoveListener?, loopInterval: Int) {
        mCallback = l
        mLoopInterval = loopInterval.toLong()
    }

    /**
     * Register a callback to be invoked when this JoystickView is touch and held by multiple pointers
     * @param l The callback that will run
     */
    fun setOnMultiLongPressListener(l: OnMultipleLongPressListener?) {
        mOnMultipleLongPressListener = l
    }

    /**
     * Set the joystick center's behavior (fixed or auto-defined)
     * @param fixedCenter True for fixed center, False for auto-defined center based on touch down
     */
    fun setFixedCenter(fixedCenter: Boolean) {
        // if we set to "fixed" we make sure to re-init position related to the width of the joystick
        if (fixedCenter) {
            initPosition()
        }
        mFixedCenter = fixedCenter
        invalidate()
    }

    /**
     * Enable or disable the joystick
     * @param enabled False mean the button won't move and onMove won't be called
     */
    override fun setEnabled(enabled: Boolean) {
        mEnabled = enabled
    }

    /**
     * Set the joystick button size (as a fraction of the real width/height)
     * By default it is 75% (0.75).
     * Not working if the background is an image.
     * @param newRatio between 0.0 and 1.0
     */
    fun setBackgroundSizeRatio(newRatio: Float) {
        if (newRatio > 0.0f && newRatio <= 1.0f) {
            mBackgroundSizeRatio = newRatio
        }
    }

    /*
    IMPLEMENTS
     */
    // Runnable
    override fun run() {
        while (!Thread.interrupted()) {
            post { mCallback?.onMove(angle, strength) }
            try {
                Thread.sleep(mLoopInterval)
            } catch (e: InterruptedException) {
                break
            }
        }
    }

    companion object {
        /*
    CONSTANTS
    */
        /**
         * Default refresh rate as a time in milliseconds to send move values through callback
         */
        private const val DEFAULT_LOOP_INTERVAL = 50 // in milliseconds

        /**
         * Used to allow a slight move without cancelling MultipleLongPress
         */
        private const val MOVE_TOLERANCE = 10

        /**
         * Default color for button
         */
        private const val DEFAULT_COLOR_BUTTON = Color.BLACK

        /**
         * Default color for border
         */
        private const val DEFAULT_COLOR_BORDER = Color.TRANSPARENT

        /**
         * Default alpha for border
         */
        private const val DEFAULT_ALPHA_BORDER = 255

        /**
         * Default background color
         */
        private const val DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT

        /**
         * Default View's size
         */
        private const val DEFAULT_SIZE = 200

        /**
         * Default border's width
         */
        private const val DEFAULT_WIDTH_BORDER = 3

        /**
         * Default behavior to fixed center (not auto-defined)
         */
        private const val DEFAULT_FIXED_CENTER = true

        /**
         * Default behavior to auto re-center button (automatically recenter the button)
         */
        private const val DEFAULT_AUTO_RECENTER_BUTTON = true

        /**
         * Default behavior to button stickToBorder (button stay on the border)
         */
        private const val DEFAULT_BUTTON_STICK_TO_BORDER = false

        /**
         * Default value.
         * Both direction correspond to horizontal and vertical movement
         */
        var BUTTON_DIRECTION_BOTH = 0
    }

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.JoystickView,
            0, 0
        )
        val buttonColor: Int
        val borderColor: Int
        val backgroundColor: Int
        val borderWidth: Int
        try {
            buttonColor = styledAttributes.getColor(R.styleable.JoystickView_JV_buttonColor, DEFAULT_COLOR_BUTTON)
            borderColor = styledAttributes.getColor(R.styleable.JoystickView_JV_borderColor, DEFAULT_COLOR_BORDER)
            mBorderAlpha = styledAttributes.getInt(R.styleable.JoystickView_JV_borderAlpha, DEFAULT_ALPHA_BORDER)
            backgroundColor = styledAttributes.getColor(R.styleable.JoystickView_JV_backgroundColor, DEFAULT_BACKGROUND_COLOR)
            borderWidth = styledAttributes.getDimensionPixelSize(R.styleable.JoystickView_JV_borderWidth, DEFAULT_WIDTH_BORDER)
            mFixedCenter = styledAttributes.getBoolean(R.styleable.JoystickView_JV_fixedCenter, DEFAULT_FIXED_CENTER)
            isAutoReCenterButton = styledAttributes.getBoolean(R.styleable.JoystickView_JV_autoReCenterButton, DEFAULT_AUTO_RECENTER_BUTTON)
            isButtonStickToBorder = styledAttributes.getBoolean(R.styleable.JoystickView_JV_buttonStickToBorder, DEFAULT_BUTTON_STICK_TO_BORDER)
            mEnabled = styledAttributes.getBoolean(R.styleable.JoystickView_JV_enabled, true)
            mButtonSizeRatio = styledAttributes.getFraction(R.styleable.JoystickView_JV_buttonSizeRatio, 1, 1, 0.25f)
            mBackgroundSizeRatio = styledAttributes.getFraction(R.styleable.JoystickView_JV_backgroundSizeRatio, 1, 1, 0.75f)
            buttonDirection = styledAttributes.getInteger(R.styleable.JoystickView_JV_buttonDirection, BUTTON_DIRECTION_BOTH)
        } finally {
            styledAttributes.recycle()
        }

        // Initialize the drawing according to attributes
        mPaintCircleButton = Paint()
        mPaintCircleButton.isAntiAlias = true
        mPaintCircleButton.color = buttonColor
        mPaintCircleButton.style = Paint.Style.FILL
        mPaintCircleBorder = Paint()
        mPaintCircleBorder.isAntiAlias = true
        mPaintCircleBorder.color = borderColor
        mPaintCircleBorder.style = Paint.Style.STROKE
        mPaintCircleBorder.strokeWidth = borderWidth.toFloat()
        if (borderColor != Color.TRANSPARENT) {
            mPaintCircleBorder.alpha = mBorderAlpha
        }
        mPaintBackground = Paint()
        mPaintBackground.isAntiAlias = true
        mPaintBackground.color = backgroundColor
        mPaintBackground.style = Paint.Style.FILL


        // Init Runnable for MultiLongPress
        mRunnableMultipleLongPress = Runnable { if (mOnMultipleLongPressListener != null) mOnMultipleLongPressListener!!.onMultipleLongPress() }
    }
}