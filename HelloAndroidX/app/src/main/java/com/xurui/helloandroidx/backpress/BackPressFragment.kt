package com.xurui.helloandroidx.backpress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xurui.helloandroidx.R
import com.xurui.helloandroidx.databinding.FragmentBackpressBinding
import com.xurui.ktx.fragmentArgument
import com.xurui.ktx.viewBinding

/**
 * Created by pengxr on 17/5/2021
 */
class BackPressFragment : Fragment(R.layout.fragment_backpress) {

    var text: String by fragmentArgument()

    private val binding by viewBinding(FragmentBackpressBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDisplay.text = text
    }
}