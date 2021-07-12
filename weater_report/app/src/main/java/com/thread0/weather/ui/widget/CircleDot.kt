/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import com.thread0.weather.R
import com.thread0.weather.util.RenderUtil.getPaint

class CircleDot(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null
    private var mStrokePaint: Paint? = null
    private var mRingPaint: Paint? = null
    private var mColorStringInner: String? = null
    private var mColorStringStroke: String? = null
    private var mColorStringRing: String? = null
    private var ableSelect = false
    private var selected = false
    private var isRing = false
    private var radius = 0f
    private var strokeRatio = 0f

    constructor(context: Context) : this(context, null, 0) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun init() {
        if (mColorStringInner == null) {
            mColorStringInner = "#ff0000"
        }
        if (mColorStringStroke == null) {
            mColorStringStroke = "#0000ff"
        }
        if (mColorStringRing == null) {
            mColorStringRing = "#00ff00"
        }
        if (strokeRatio > 1) {
            strokeRatio = 0.02f
        }
        mPaint = getPaint(Color.parseColor(mColorStringInner))
        mPaint!!.style = Paint.Style.FILL
        mStrokePaint = getPaint(Color.parseColor(mColorStringStroke))
        mStrokePaint!!.style = Paint.Style.STROKE
        mStrokePaint!!.strokeWidth = radius * strokeRatio

        mRingPaint = getPaint(Color.parseColor(mColorStringRing))
        mRingPaint!!.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        radius = measuredWidth.coerceAtMost(measuredHeight) / 2f - radius*strokeRatio/2
        mStrokePaint!!.strokeWidth = radius * strokeRatio
        val x = measuredWidth / 2f
        val y = measuredHeight / 2f

        val startX = x - radius * (1 - strokeRatio / 2)-radius*strokeRatio/2
        val startY = y - radius * (1 - strokeRatio / 2)-radius*strokeRatio/2
        val endX = x + radius * (1 - strokeRatio / 2)+radius*strokeRatio/2
        val endY = y + radius * (1 - strokeRatio / 2)+radius*strokeRatio/2

        if (ableSelect) {
            if (selected) {
                canvas.drawCircle(x, y, radius * (1 - strokeRatio), mPaint!!)
                canvas.drawArc(startX,startY,endX,endY, 0F,360F,false,mStrokePaint!!)
            } else {
                if (isRing){
                    canvas.drawArc(startX+radius*strokeRatio/2,startY+radius*strokeRatio/2,endX-radius*strokeRatio/2,endY-radius*strokeRatio/2, 0F,360F,false,mRingPaint!!)
                }else {
                    canvas.drawCircle(x, y, radius * (1 - strokeRatio), mPaint!!)
                }
            }
        } else {
            if (isRing){
                canvas.drawArc(startX+radius*strokeRatio/2,startY+radius*strokeRatio/2,endX-radius*strokeRatio/2,endY-radius*strokeRatio/2, 0F,360F,false,mRingPaint!!)
            }else{
                canvas.drawCircle(x, y, radius, mPaint!!)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_UP -> if (ableSelect) {
                selected = if (selected) {
                    false
                } else {
                    listener?.onClick()
                    true
                }
                invalidate()
            } else {
                listener?.onClick()
            }
        }
        return true
    }

    //点击事件
    interface OnClickListener {
        fun onClick()
    }

    private var listener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val width = (radius * 2+radius*strokeRatio/2).toInt()
        val height = (radius * 2+radius*strokeRatio/2).toInt()

        // get calculate mode of width and height
        var modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        var modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        // get recommend width and height
        var sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        var sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (modeWidth == MeasureSpec.AT_MOST) { // wrap_content
            sizeWidth = Math.min(width, sizeWidth)
            modeWidth = MeasureSpec.EXACTLY
        }
        if (modeHeight == MeasureSpec.AT_MOST) { // wrap_content
            sizeHeight = Math.min(height, sizeHeight)
            modeHeight = MeasureSpec.EXACTLY
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setColorStringInner(color: String?) {
        mColorStringInner = color
        init()
        invalidate()
    }

    fun setColorStringStroke(color: String?) {
        mColorStringStroke = color
        init()
        invalidate()
    }

    fun setRadius(radius: Float) {
        this.radius = radius
        init()
        invalidate()
    }

    fun setAbleSelect(ableSelect: Boolean) {
        this.ableSelect = ableSelect
        init()
        invalidate()
    }

    override fun setSelected(selected: Boolean) {
        this.selected = selected
        init()
        invalidate()
    }

    fun setStrokeRatio(strokeRatio: Float) {
        this.strokeRatio = strokeRatio
        init()
        invalidate()
    }

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleDot)
        mColorStringInner = typedArray.getString(R.styleable.CircleDot_inner_color)
        mColorStringStroke = typedArray.getString(R.styleable.CircleDot_stroke_color)
        mColorStringRing = typedArray.getString(R.styleable.CircleDot_ring_color)
        ableSelect = typedArray.getBoolean(R.styleable.CircleDot_able_select, false)
        selected = typedArray.getBoolean(R.styleable.CircleDot_selected, false)
        isRing = typedArray.getBoolean(R.styleable.CircleDot_ring, false)
        radius = typedArray.getFloat(R.styleable.CircleDot_radius, 12f)
        strokeRatio = typedArray.getFloat(R.styleable.CircleDot_stroke_ratio, 0.02f)
        typedArray.recycle()
        init()
    }
}