package com.xurui.hellojni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "peng"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jni = HelloWorld()
        jni.sayHi()

        // 访问静态字段和动态字段
        jni.accessField()
        Log.d(TAG, HelloWorld.getsName())
        Log.d(TAG, jni.getmName())

    }
}