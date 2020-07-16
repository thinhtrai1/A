package com.a.activities

import android.app.Application
import com.a.utils.SharedPreferenceHelper

class Application: Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPreferenceHelper.getInstance = SharedPreferenceHelper(this)
    }
}