package com.xurui.helloandroidx.backpress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.os.Process.killProcess
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.xurui.helloandroidx.R
import com.xurui.helloandroidx.databinding.ActivityBackpressBinding
import com.xurui.ktx.viewBinding
import kotlin.system.exitProcess

/**
 * Created by pengxr on 17/5/2021
 */
fun Context.startBackPressActivity() {
    startActivity(Intent(this, BackPressActivity::class.java))
}

class BackPressActivity : AppCompatActivity(R.layout.activity_backpress) {

    private val binding by viewBinding(ActivityBackpressBinding::bind)

    /**
     * 上次点击返回键的时间
     */
    private var lastBackPressTime = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragmentToStack()
        onBackPressedDispatcher.addCallback(this, onBackPress)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addFragmentToStack() {
        // 提示：为了聚焦问题，这里不考虑 Activity 重建的场景
        for (index in 1..5) {
            supportFragmentManager.beginTransaction().let { it ->
                it.add(
                    R.id.container,
                    BackPressFragment().also { it.text = "fragment_$index" },
                    "fragment_$index"
                )
                it.addToBackStack(null)
                it.commit()
            }
        }
    }

    /**
     * @return true:没有Fragment弹出 false:有Fragment弹出
     */
    private fun popBackStack(): Boolean {
        // 当 Fragment 状态保存了
        return supportFragmentManager.isStateSaved
                || supportFragmentManager.popBackStackImmediate()
    }

    private val onBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (popBackStack()) {
                return
            }
            val currentTIme = System.currentTimeMillis()
            if (lastBackPressTime == -1L || currentTIme - lastBackPressTime >= 2000) {
                // 显示提示信息
                showBackPressTip()
                // 记录时间
                lastBackPressTime = currentTIme
            } else {
                //退出应用
                finish()
                // android.os.Process.killProcess(android.os.Process.myPid())
                // System.exit(0) // exitProcess(0)
                // moveTaskToBack(false)
            }
        }
    }

    private fun showBackPressTip() {
        Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
    }
}