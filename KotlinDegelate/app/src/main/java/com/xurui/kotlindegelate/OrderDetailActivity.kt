package com.xurui.kotlindegelate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by pengxr on 7/5/2021
 */
fun Context.startOrderDetail(orderId: Int, orderType: Int?) {
    startActivity(Intent(this, OrderDetailActivity::class.java).apply {
        this["orderId"] = orderId
        this["orderType"] = orderType
    })
}

class OrderDetailActivity : AppCompatActivity() {

    private val orderId: Int by activityArgument()
    private val orderType: Int? by activityArgumentNullable(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_order_detail)

        with(supportFragmentManager.beginTransaction()) {
            val fragment = OrderDetailFragment.newInstance(orderId, orderType)
            add(R.id.containerId, fragment)
            commit()
        }
    }
}

