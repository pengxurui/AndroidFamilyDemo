package com.xurui.kotlindelegate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.xurui.kotlindelegate.databinding.DialogOrderDetailBinding
import com.xurui.ktx.property.viewBinding

/**
 * Created by pengxr on 11/5/2021
 */
class OrderDetailDialogFragment : DialogFragment() {

    private val binding by viewBinding(DialogOrderDetailBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDisplay.text = "Hello Dialog."
    }
}