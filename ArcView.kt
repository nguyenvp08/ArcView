package com.kotlin.andyho.animationdemo.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.kotlin.andyho.animationdemo.R

class ArcView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    var rectF: RectF = RectF()
    var paint: Paint = Paint()
    var rectText: Rect = Rect()

    var backgroundColorAtt: Int? = null
    var textSizeAtt: Float? = null
    var textColorAtt: Int? = null
    var textAtt: String? = null
    var degreeAtt: Int? = null
    var borderPaddingAtt: Float? = null

    init {
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
        }
        if (attrs != null) {
            context?.obtainStyledAttributes(attrs, R.styleable.ArcView, 0, 0)
                .let {
                    backgroundColorAtt = it?.getColor(R.styleable.ArcView_arcColor, 0)
                    textSizeAtt = it?.getDimension(R.styleable.ArcView_arcTextSize, 0F)
                    textColorAtt = it?.getColor(R.styleable.ArcView_arcTextColor, 0)
                    textAtt = it?.getString(R.styleable.ArcView_arcText)
                    degreeAtt = it?.getInt(R.styleable.ArcView_arcDegree, 0)
                    borderPaddingAtt = it?.getDimension(R.styleable.ArcView_arcBorderPadding, 0F)
                    it?.recycle()
                }
        }
        paint.textSize = textSizeAtt ?: 0F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val borderSize: Float = borderPaddingAtt ?: 0F
        rectF.set(0F + paddingLeft + borderSize, 0F + paddingTop + borderSize, w.toFloat() - paddingRight - borderSize, h.toFloat() - paddingBottom - borderSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = backgroundColorAtt ?: 0
        val degree = degreeAtt?.toFloat() ?: 0F
        canvas?.drawArc(rectF, 90 - degree, degree * 2F, false, paint)
        paint.getTextBounds(textAtt, 0, textAtt?.length ?: 0, rectText)

        // calculate text position
        val yPos = rectF.top + rectF.height() * 3 / 4 + rectF.height() * Math.cos(Math.toRadians(degree.toDouble())) / 4 + rectText.height() / 2
        val xPos = rectF.width() / 2 - rectText.width() / 2
        paint.color = textColorAtt ?: 0
        canvas?.drawText(textAtt, xPos, yPos.toFloat(), paint)
    }
}
