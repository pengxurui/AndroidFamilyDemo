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

        // 访问静态字段和实例字段
        jni.accessField()
        Log.d(TAG, HelloWorld.getsName())
        Log.d(TAG, jni.getmName())

        // 访问静态方法和实例方法
        jni.accessMethod()

    }
}