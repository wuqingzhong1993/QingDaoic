package com.outsource.danding.qingdaoic.app

import android.app.Application
import android.content.Context


class QdApp:Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}