package com.sixdays.truckerpath.renren.view.base.recycleview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import com.yange.myapplication.R

class SectionDecoration(context: Context, private val callback: DecorationCallback) :
    RecyclerView.ItemDecoration() {

    private val textPaint: TextPaint
    private val paint: Paint = Paint()
    private val topGap: Int
    private val fontMetrics: Paint.FontMetrics

    init {
        paint.color = ContextCompat.getColor(context, R.color.day_f8f9fc_night_131414)
        fontMetrics = Paint.FontMetrics()
        topGap = context.resources.getDimensionPixelSize(R.dimen.section_decoration_top)
        textPaint = TextPaint()
        //textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.isAntiAlias = true
        textPaint.textSize = 40F
        textPaint.color = ContextCompat.getColor(context, R.color.day_141a42_night_a4a5b1)
        textPaint.getFontMetrics(fontMetrics)
        textPaint.textAlign = Paint.Align.LEFT
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        val groupId = callback.getGroupId(pos)
        if (groupId < 0) return
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap
        } else {
            outRect.top = 0
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val itemCount = state.itemCount
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val lineHeight = textPaint.textSize + fontMetrics.descent

        var preGroupId: Long
        var groupId: Long = -1
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)

            preGroupId = groupId
            groupId = callback.getGroupId(position)
            if (groupId < 0 || groupId == preGroupId) continue

            val textLine = callback.getGroupFirstLine(position).toUpperCase()
            if (TextUtils.isEmpty(textLine)) continue

            val viewBottom = view.bottom
            var textY = Math.max(topGap, view.top)
            if (position + 1 < itemCount) {
                val nextGroupId = callback.getGroupId(position + 1)
                if (nextGroupId != groupId && viewBottom < textY) {
                    textY = viewBottom
                }
            }
            canvas.drawRect(
                left.toFloat(), (textY - topGap).toFloat()
                , right.toFloat(), textY.toFloat(), paint
            )
            canvas.drawText(
                textLine,
                left.toFloat() + 30,
                textY.toFloat() - lineHeight / 2 - 5,
                textPaint
            )
        }
    }

    private fun isFirstInGroup(pos: Int): Boolean {
        return if (pos == 0) {
            true
        } else {
            val prevGroupId = callback.getGroupId(pos - 1)
            val groupId = callback.getGroupId(pos)
            prevGroupId != groupId
        }
    }

    interface DecorationCallback {

        fun getGroupId(position: Int): Long

        fun getGroupFirstLine(position: Int): String
    }

}