package com.pengxr.helloleakcanary

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kwai.koom.base.DefaultInitTask
import com.kwai.koom.fastdump.ForkJvmHeapDumper
import leakcanary.AppWatcher
import leakcanary.EventListener
import leakcanary.LeakCanary
import shark.AppSingletonInspector
import shark.ObjectInspector
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * 在官方 Demo 的基础上修改
 */
class MainActivity : AppCompatActivity(), EventListener {

    private var leakyReceiver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as ExampleApplication
        // 自定义 LeakCanary 配置
        findViewById<Button>(R.id.leak_config_button).setOnClickListener {
            // 使用默认配置初始化 Koom
            DefaultInitTask.init(application)
            // 自定义 LeakCanary 配置
            LeakCanary.config = LeakCanary.config.copy(
                // 自定义计数阈值
                retainedVisibleThreshold = 3,
                // 自定义事件监听器
                eventListeners = LeakCanary.config.eventListeners + this,
                // 自定义 Heap Dump 执行器
                heapDumper = {
                    ForkJvmHeapDumper.getInstance().dump(it.absolutePath)
                },
                // 自定义
                objectInspectors = LeakCanary.config.objectInspectors + ObjectInspector { reporter ->
                    // reporter.notLeakingReasons += "非泄漏原因"
                    // reporter.leakingReasons += "泄漏原因"
                } + AppSingletonInspector(
                    // 标记全局类的类名即可
                )
            )
        }

        // Activity 重建
        findViewById<Button>(R.id.recreate_activity_button).setOnClickListener { recreate() }

        // Activity 泄漏
        findViewById<Button>(R.id.leak_activity_button).setOnClickListener {
            val leakedView = findViewById<View>(R.id.helper_text)
            when (Random.nextInt(4)) {
                // Leak from application class
                0 -> app.leakedViews.add(leakedView)
                // Leak from Kotlin object singleton
                1 -> LeakingSingleton.leakedViews.add(leakedView)
                2 -> {
                    // Leak from local variable on thread
                    val ref = AtomicReference(this)
                    thread(name = "Leaking local variables") {
                        val activity = ref.get()
                        ref.set(null)
                        while (true) {
                            print(activity)
                            SystemClock.sleep(1000)
                        }
                    }
                }
                // Leak from thread fields
                else -> LeakingThread.thread.leakedViews.add(leakedView)
            }
        }

        // Dialog 泄漏
        findViewById<Button>(R.id.show_dialog_button).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Leaky dialog")
                .setPositiveButton("Dismiss and leak dialog") { dialog, _ ->
                    app.leakedDialogs += dialog as AlertDialog
                }
                .show()
        }

        // Service 泄漏
        findViewById<Button>(R.id.start_service_button).setOnClickListener {
            startService(Intent(this, LeakingService::class.java))
        }

        // BroadcastReceiver 泄漏
        findViewById<Button>(R.id.leak_receiver_button).setOnClickListener {
            leakyReceiver = true
            recreate()
        }

        // 延迟 Handler 消息造成 Activity 泄漏
        findViewById<Button>(R.id.message_leak_button).setOnClickListener {
            val leaky = Any()
            AppWatcher.objectWatcher.expectWeaklyReachable(leaky, "Repeated Message")
            @Suppress("unused")
            class LeakyReschedulingRunnable(private val leaky: Any) : Runnable {
                private val handler = Handler(Looper.getMainLooper())
                override fun run() {
                    handler.postDelayed(this, 10000)
                }
            }
            LeakyReschedulingRunnable(leaky).run()
        }

        // 动画造成 Activity 泄漏
        findViewById<Button>(R.id.infinite_animator).setOnClickListener { view ->
            ObjectAnimator.ofFloat(view, View.ALPHA, 0.1f, 0.2f).apply {
                duration = 100
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                start()
            }
        }

        findViewById<Button>(R.id.finish_activity).setOnClickListener { view ->
            finish()
        }
    }

    class NoOpBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) = Unit
    }

    override fun onDestroy() {
        super.onDestroy()
        if (leakyReceiver) {
            Handler().postDelayed({
                registerReceiver(NoOpBroadcastReceiver(), IntentFilter())
            }, 500)
        }
    }

    override fun onEvent(event: EventListener.Event) {
        Log.d("XIAOPENG", event.toString())
    }
}
