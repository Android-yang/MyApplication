package com.yange.myapplication.widget

import android.content.Context
import android.support.v7.appcompat.R
import android.util.AttributeSet
import android.widget.TextView


class TextInputEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle) :
        android.support.design.widget.TextInputEditText(context, attrs, defStyleAttr) {

    var isTextChangedByUser = true

    override fun setText(text: CharSequence?, type: TextView.BufferType?) {
        isTextChangedByUser = false
        super.setText(text, type)
        isTextChangedByUser = true
    }
}