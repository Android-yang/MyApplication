package com.yange.myapplication.base

import android.app.Application
import android.content.Context

/**
 * author : yangke on 2019-11-19
 * weChat : ACE_5200125
 * email  : 211yangke@gmail.com
 * desc   : Application
 * version: v1.0
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var TAG = "yangke-"
        lateinit var appContext: Context
    }
}