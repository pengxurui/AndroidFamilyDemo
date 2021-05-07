package com.xurui.kotlindegelate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * <p>
 * Created by pengxr on 7/5/2021
 */
class OrderDetailFragment : Fragment() {

    private lateinit var tvDisplay: TextView

    private var orderId: Int by fragmentArgument()
    private var orderType: Int? by fragmentArgumentNullable(2)

    companion object {
        fun newInstance(orderId: Int, orderType: Int?) = OrderDetailFragment().apply {
            this.orderId = orderId
            this.orderType = orderType
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_order_detail, container, false)
        // Try to modify (UnExcepted)
        this.orderType = 3
        // Display Value
        tvDisplay = root.findViewById(R.id.tv_display)
        tvDisplay.text = "orderId = $orderId, orderType = $orderType"
        return root
    }
}