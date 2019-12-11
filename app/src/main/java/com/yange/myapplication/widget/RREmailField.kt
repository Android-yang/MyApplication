package com.yange.myapplication.widget

import android.view.View
import com.yange.myapplication.R

import java.util.regex.Pattern

class RREmailField @JvmOverloads constructor(view: RRTextInputLayout, optional: Boolean = false, emptyIndicatorView: View? = null)
    : RRTextField(view, view.context.getString(R.string.error_invalid_email), optional, emptyIndicatorView) {

    override fun isValidIfMandatory(): Boolean {
        return super.isValidIfMandatory() && emailPattern.matcher(text).matches()
    }

    companion object {

        private val emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    }
}
