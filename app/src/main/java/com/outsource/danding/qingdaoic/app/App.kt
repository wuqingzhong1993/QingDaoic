package com.outsource.danding.qingdaoic.app

import android.app.Application
import android.content.Context
import com.outsource.danding.qingdaoic.bean.Department


class QdApp:Application() {

    val departments= mutableListOf<Department>()

    companion object {
        lateinit var appContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

    }

}