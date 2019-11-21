package com.yange.myapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.yange.myapplication.R

/**
 * author : yangke on 2019-11-19
 * weChat : ACE_5200125
 * email  : 211yangke@gmail.com
 * desc   : 支持红点，消息数量的TextView
 * version:v1.0
 */
class BadgeTextView(ctx: Context, attr: AttributeSet) : TextView(ctx, attr) {

    /**
     * 配置通知数量
     */
    var mNum = 0
        set(value) {
            field = value
            invalidate()
        }
    /**
     * 配置通知红点
     * */
    var mShowRedPoint = false
        set(value) {
            mNum = 0
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(mShowRedPoint){
            drawCircle(resources.getDimensionPixelSize(R.dimen.dp2).toFloat(), canvas)
            return
        }
        if (mNum > 0) {
            val pair = drawCircle(width / 4f, canvas)
            var radius = pair.first
            val paint = pair.second
            var textSize = resources.getDimensionPixelSize(R.dimen.dp10)
            var xPosition = 0f
            paint.color = resources.getColor(R.color.community_notice_corner_text)
            if (mNum < 10) {
                xPosition = width - radius - textSize / 4 - paddingRight / 2
            } else if (mNum < 99) {
                xPosition = width - radius - textSize / 2 - paddingRight / 2 - 1
            } else {
                xPosition = width - radius - textSize / 2 - paddingRight / 2 - 3
            }

            var showTextStr = ""
            if (mNum < 99) {
                showTextStr = "${mNum}"
            } else {
                textSize = resources.getDimensionPixelSize(R.dimen.dp8)
                showTextStr = "99+"
            }

            paint.textSize = textSize.toFloat()
            canvas.drawText(
                showTextStr
                , xPosition
                , radius + textSize / 3 + paddingTop / 2
                , paint
            )
        }
    }

    private fun drawCircle(radius : Float, canvas: Canvas): Pair<Float, Paint> {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = resources.getColor(R.color.community_notice_corner_bg)
        paint.style = Paint.Style.FILL

        canvas.drawCircle(
            width - radius - paddingRight / 2,
            radius + paddingTop / 2,
            radius,
            paint
        )
        return Pair(radius, paint)
    }
}