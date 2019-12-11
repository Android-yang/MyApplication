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
            verify_code_edit.postDelayed(Runnable { verify_code_edit.setText(null) }, 500)
            notice_iv.mShowRedPoint = true
        }
        var error = "Verification code incorrect"
        verify_code_edit.setError(true);
    }
}
