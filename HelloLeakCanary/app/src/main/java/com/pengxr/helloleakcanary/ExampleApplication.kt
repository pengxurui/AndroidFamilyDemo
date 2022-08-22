package com.pengxr.helloleakcanary

import android.app.Application
import android.app.Dialog
import android.app.Service
import android.os.StrictMode
import android.view.View
import leakcanary.LeakCanaryProcess

open class ExampleApplication : Application() {
    val leakedViews = mutableListOf<View>()
    val leakedDialogs = mutableListOf<Dialog>()
    val leakedServices = mutableListOf<Service>()

    override fun onCreate() {
        if (LeakCanaryProcess.isInAnalyzerProcess(this)) {
            return
        }
        super.onCreate()
        // enabledStrictMode()
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}
