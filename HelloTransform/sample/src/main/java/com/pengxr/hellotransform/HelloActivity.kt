package com.pengxr.hellotransform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by pengxr on 15/5/2022
 */
class HelloActivity : AppCompatActivity() {

    @Hello
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
    }
}