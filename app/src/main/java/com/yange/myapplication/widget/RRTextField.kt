package com.yange.myapplication.widget

import android.os.Bundle
import android.support.annotation.CallSuper
import android.text.Editable
import android.text.TextWatcher
import android.view.View

open class RRTextField @JvmOverloads constructor(view: RRTextInputLayout,
                                                 defaultErrorMessage: String,
                                                 optional: Boolean = false,
                                                 emptyIndicatorView: View? = null)
    : BaseField<RRTextInputLayout>(view, defaultErrorMessage, optional, emptyIndicatorView), TextWatcher {

    private var isSetTextRunning = false

    private var initialValue: String? = null

    override val isChanged: Boolean
        get() = initialValue != view.getmEditText()?.text.toString()

    private val isTextChangedByUser: Boolean
        get() = (view.getmEditText() as TextInputEditText).isTextChangedByUser

    fun init(text: String?, savedInstanceState: Bundle?) {
        initialValue = text
        view.getmEditText()?.onFocusChangeListener = this
        view.getmEditText()?.addTextChangedListener(this)
        if (savedInstanceState == null) {
            this.text = text
        }
    }

    var text: String?
        get() = view.getmEditText()?.text.toString().trim { it <= ' ' }
        private set(text) {
            isSetTextRunning = true
            view.getmEditText()?.setText(text)
            isSetTextRunning = false
        }

    override fun setError(error: String) {
        super.setError(error)
        view.setError(error)
    }

    override fun resetError() {
        super.resetError()
        view.clearError()
    }

    @CallSuper
    override fun isValidIfMandatory() = !isEmpty

    override val isEmpty: Boolean
        get() = text.isNullOrBlank()

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        emptyIndicatorView?.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
    }

    override fun afterTextChanged(s: Editable) {
        if (isTextChangedByUser) {
            listener?.onChanged(this)
        }
    }

    override fun requestFocus() {
        super.requestFocus()

        view.getmEditText()?.setSelection(view.getmEditText()?.length() ?: 0)
    }
}
