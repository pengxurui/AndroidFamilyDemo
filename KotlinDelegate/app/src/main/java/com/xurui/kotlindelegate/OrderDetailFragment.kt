package com.xurui.kotlindelegate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xurui.kotlindelegate.databinding.FragmentOrderDetailBinding
import com.xurui.ktx.property.argument
import com.xurui.ktx.property.viewBinding

/**
 * <p>
 * Created by pengxr on 7/5/2021
 */
class OrderDetailFragment : Fragment(R.layout.fragment_order_detail) {

    private val binding by viewBinding(FragmentOrderDetailBinding::bind)

    private var orderId: Int by argument()
    private var orderType: Int by argument(1)

    companion object {
        fun newInstance(orderId: Int, orderType: Int?) = OrderDetailFragment().apply {
            this.orderId = orderId
            orderType?.also { this.orderType = it }
        }
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        // Try to modify (UnExcepted)
        // this.orderType = 3
        // Display Value
        binding.tvDisplay.text = "orderId = $orderId, orderType = $orderType"
    }
}