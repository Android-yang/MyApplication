package com.yange.myapplication

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * author : yangke on 2019-11-12
 * weChat : ACE_5200125
 * email  : 211yangke@gmail.com
 * desc   :
 * version:
 */
class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var mTitle: TextView
    var mSubtitle: TextView? = null

    init {
        mTitle = v.findViewById<View>(R.id.title) as TextView
        //        mSubtitle = (TextView) v.findViewById(R.id.subtitle);
    }
}
