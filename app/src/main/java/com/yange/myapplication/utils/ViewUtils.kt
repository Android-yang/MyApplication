package com.yange.myapplication.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import com.yange.myapplication.R
import com.yange.myapplication.base.BaseApp


/**
 * author : yangke on 2019-11-19
 * weChat : ACE_5200125
 * email  : 211yangke@gmail.com
 * desc   : 配置View样式
 * version: v1.0
 */
object ViewUtils {

    /**
     * 配置部分文本可点击
     */
    fun configUrl(tv: TextView, clickableSpan: ClickableSpan?, start: Int, end: Int, color: Int) {
        tv.highlightColor = Color.TRANSPARENT//禁止按下时选中的背景色
        tv.movementMethod = LinkMovementMethod.getInstance()
        var type = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        var style = SpannableStringBuilder()
        style.append(tv.text)
        style.setSpan(clickableSpan, start, end, type)//设置点击
        style.setSpan(ForegroundColorSpan(color), start, end, type)//设置链接颜色
        tv.text = style
    }

    /**
     * log d
     */
    fun d(obj: Any) {
        if (obj is String) {
            Log.d(BaseApp.TAG, obj)
        }
        else {
            Log.d(BaseApp.TAG, obj.toString())
        }
    }

    /**
     * @param str
     * @return
     */
    fun addClickablePart(str: String): SpannableStringBuilder {
        var span = ImageSpan(BaseApp.appContext, R.drawable.notice)//赞图标
        var spanStr = SpannableString("p")
        spanStr.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        var ssb = SpannableStringBuilder(spanStr)
        ssb.append(str)
        var likeUsers = str.split("、")
        if (likeUsers.isNotEmpty()) {
            // 最后一个
            for (element in likeUsers) {
                var name = element
                var start = str.indexOf(name) + spanStr.length
                ssb.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) = BaseApp.appContext.toast(name)

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.RED // 设置文本颜色
                        ds.isUnderlineText = false// 去掉下划线
                    }

                }, start, start + name.length, 0)
            }
        }
        return ssb.append("等${likeUsers.size}个人赞了您.")
    }
}