package com.xurui.helloandroidx.backpress

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.xurui.helloandroidx.R
import com.xurui.helloandroidx.databinding.FragmentBackpressBinding
import com.xurui.ktx.fragmentArgument
import com.xurui.ktx.viewBinding

/**
 * Created by pengxr on 17/5/2021
 */
class BackPressFragment : Fragment(R.layout.fragment_backpress) {

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }

    var text: String by fragmentArgument()

    private val binding by viewBinding(FragmentBackpressBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "BackPressFragment - handleOnBackPressed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDisplay.text = text
    }
}