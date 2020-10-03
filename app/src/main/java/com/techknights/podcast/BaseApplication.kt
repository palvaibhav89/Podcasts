package com.techknights.podcast

import android.app.Application
import com.techknights.podcast.utils.PrefUtils

class BaseApplication : Application() {

    companion object {
        lateinit var sharedPref: PrefUtils
    }

    override fun onCreate() {
        super.onCreate()

        sharedPref = PrefUtils(applicationContext)
    }
}