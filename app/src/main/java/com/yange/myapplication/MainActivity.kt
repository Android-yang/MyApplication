package com.yange.myapplication

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.yange.myapplication.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notice_iv.mNum = 90
        bt1.setOnClickListener {
            notice_iv.mShowRedPoint = false
        }
        bt2.setOnClickListener {
            notice_iv.mShowRedPoint = true
        }


        var sbBuilder = StringBuilder()
        for (i in 0..10) {
            sbBuilder.append("user$i、")
        }
        var likeUsers = sbBuilder.substring(0, sbBuilder.lastIndexOf("、")).toString()
        tv1.movementMethod = LinkMovementMethod.getInstance()
        tv1.highlightColor = Color.TRANSPARENT
        tv1.setText(ViewUtils.addClickablePart(likeUsers), TextView.BufferType.SPANNABLE)

    }
}
