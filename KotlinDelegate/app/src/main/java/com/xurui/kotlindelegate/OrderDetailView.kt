package com.xurui.kotlindelegate

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.xurui.kotlindelegate.databinding.LayoutOrderBinding
import com.xurui.ktx.property.viewBinding

/**
 * Created by pengxr on 21/6/2021
 */
class OrderDetailView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val binding by viewBinding(LayoutOrderBinding::bind)

    init {
        inflate(context, R.layout.layout_order, this)
        orientation = VERTICAL
    }

    fun setOrderId(value: Int) {
        binding.tvOrderId.text = "orderId = $value"
    }

    fun setOrderType(value: Int) {
        binding.tvOrderType.text = "orderType = $value"
    }
}