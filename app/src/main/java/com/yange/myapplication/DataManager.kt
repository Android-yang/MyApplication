package com.yange.myapplication

import java.io.Serializable

/**
 * author : yangke on 2019-11-13
 * weChat : ACE_5200125
 * email  : 211yangke@gmail.com
 * desc   :
 * version:
 */
class DataManager {

    private var mList = arrayListOf<ResumeType>()
    fun getFreightType(): ArrayList<ResumeType> {
        mList.clear()
        mList.add(ResumeType("Dry Van", false))
        mList.add(ResumeType("Reefer", false))
        mList.add(ResumeType("Drybulk", false))
        mList.add(ResumeType("Flatbed", false))
        mList.add(ResumeType("Tanker", false))
        mList.add(ResumeType("Car Hauler", false))
        mList.add(ResumeType("Overdimentioanl", false))
        mList.add(ResumeType("Intermodel", false))
        return mList
    }


    companion object {
        @JvmStatic
        val instance: DataManager by lazy { DataManager() }
    }
}

class ResumeType(type: String, isChecked: Boolean) : Serializable {
    var type: String = type
    var isChecked: Boolean = isChecked
}
