package com.yange.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
    }
}
