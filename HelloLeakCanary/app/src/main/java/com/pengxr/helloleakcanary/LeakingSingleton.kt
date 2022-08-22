package com.pengxr.helloleakcanary

import android.view.View

object LeakingSingleton {
    val leakedViews = mutableListOf<View>()
}