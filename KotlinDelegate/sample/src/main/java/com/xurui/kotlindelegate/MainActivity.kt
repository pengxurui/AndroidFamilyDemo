package com.xurui.kotlindelegate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xurui.kotlindelegate.databinding.ActivityMainBinding
import com.xurui.ktx.property.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.tvDisplay.text = "Main Hello World."

        startOrderDetail(10000, null)
    }
}