package com.xurui.mavenpublish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xurui.aarlib.aarlib2Test
import com.xurui.lib.PublishActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 测试直接依赖
        val activity: PublishActivity? = null
        // 测试间接依赖
        val aarlib2Test = aarlib2Test()
        aarlib2Test.start(this)
    }
}