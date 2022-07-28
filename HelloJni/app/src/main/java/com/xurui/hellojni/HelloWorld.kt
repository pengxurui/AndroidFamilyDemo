package com.xurui.hellojni

import android.util.Log

/**
 * Created by pengxr on 28/7/2022
 */
class HelloWorld {

    private val mName = "初始值"
    private var mStringArray: Array<String>? = null
    private var mIntArray: IntArray? = null

    fun getName(): String {
        return mName
    }

    companion object {
        const val TAG = "HelloJNI"

        // 如果使用 const val 或 static final 修饰（静态常量），则无法从 Native 层修改
        private val sName = "初始值"
        fun getsName(): String {
            return sName
        }

        // Kotlin static 需要使用 @JvmStatic 修饰，否则该方法会放在 Companion 中，而不是直接放在当前类中
        @JvmStatic
        fun sHelloJava() {
            Log.d(TAG, "Native 调用 Java 静态方法 helloJava")
        }

        init {
            // 加载 so 库
            System.loadLibrary("Hello-World")
        }
    }

    fun callNative() {
        // 演示 Java 调用 Native 方法
        sayHi()
        // 演示 Native 访问 Java 静态字段和实例字段
        accessField()
        Log.d(TAG, "静态字段：" + getsName())
        Log.d(TAG, "实例字段：$mName")
        // 演示 Native 访问 Java 静态方法和实例方法
        accessMethod()
        // 演示 Native 操作基本类型数组
        mIntArray = generateIntArray(10)
        // 演示 Native 操作引用类型数组
        mStringArray = generateStringArray(10)
        Log.d(TAG, "基础类型数组：" + mIntArray?.joinToString())
        Log.d(TAG, "引用类型数组：" + mStringArray?.joinToString())
    }

    external fun sayHi()
    external fun accessField()
    external fun accessMethod()
    external fun generateIntArray(size: Int): IntArray
    external fun generateStringArray(size: Int): Array<String>

    private fun helloJava() {
        Log.d(TAG, "Native 调用 Java 实例方法 helloJava")
    }
}
