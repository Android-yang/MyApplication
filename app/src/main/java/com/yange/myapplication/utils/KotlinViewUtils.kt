package com.yange.myapplication.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v4.app.Fragment
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import com.yange.myapplication.base.BaseApp

fun Fragment.toastShort(resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastShort(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastLong(resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
}

fun Fragment.toastLong(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Context.toastShort(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun copyToClipboard(text: String) {
    val clipboard = BaseApp.appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("", text)
    clipboard.primaryClip = clip
}

fun TextView.setHtmlText(htmlText: String) {
    text = Html.fromHtml(htmlText).trim()
}