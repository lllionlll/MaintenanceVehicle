package io.maintenancevehicle

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.maintenancevehicle.utils.SharedPreferences

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferences.init(this)
    }
}