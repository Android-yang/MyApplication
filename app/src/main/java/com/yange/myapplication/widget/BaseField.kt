package com.yange.myapplication.widget

import android.support.annotation.CallSuper
import android.view.View

abstract class BaseField<out T : View> @JvmOverloads constructor(val view: T,
                                                                 private val defaultErrorMessage: String,
                                                                 open val isOptional: Boolean = true,
                                                                 protected val emptyIndicatorView: View? = null)
    : View.OnFocusChangeListener {

    abstract val isChanged: Boolean

    var isEnabled: Boolean = true
        set(value) {
            field = value
            onEnable(field)
        }

    @CallSuper
    open protected fun onEnable(enable: Boolean) {
        view.visibility = if (enable) View.VISIBLE else View.GONE
        emptyIndicatorView?.visibility = if (enable) View.VISIBLE else View.GONE
    }

    var listener: IListener? = null

    var isErrorSet = false
        private set

    open val isEmpty: Boolean
        get() = true

    @CallSuper
    open fun setError(error: String) {
        isErrorSet = true
    }

    open fun setDefaultError() {
        setError(defaultErrorMessage)
    }

    @CallSuper
    open fun resetError() {
        isErrorSet = false
    }

    open fun validate() = (isOptional && isEmpty) || isValidIfMandatory()
    open fun validateOnFieldChange() = validate()
    open protected fun isValidIfMandatory(): Boolean = true

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus && !isEmpty) {
            if (validateOnFieldChange()) {
                resetError()
            } else {
                setDefaultError()
            }
        }
    }

    interface IListener {
        fun onChanged(field: BaseField<*>)
    }

    @CallSuper
    open fun requestFocus() {
        view.requestFocus()
    }

}
