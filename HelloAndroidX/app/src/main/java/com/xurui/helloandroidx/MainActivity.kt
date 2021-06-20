package com.xurui.helloandroidx

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xurui.helloandroidx.backpress.startBackPressActivity
import com.xurui.helloandroidx.databinding.ActivityMainBinding
import com.xurui.ktx.property.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testCase = listOf(
            TestCase("OnBackPressedDispatcher") {
                startBackPressActivity()
            }
        )

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = TestAdapter().apply {
            data = testCase
        }
    }

    private class TestAdapter : RecyclerView.Adapter<TestCaseVH>() {
        var data: List<TestCase>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TestCaseVH(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 60)
            })

        override fun onBindViewHolder(holder: TestCaseVH, position: Int) =
            holder.bind(data!![position])

        override fun getItemCount() = data?.size ?: 0
    }

    private class TestCaseVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var mItem: TestCase

        init {
            itemView.setOnClickListener {
                mItem.callback()
            }
        }

        fun bind(item: TestCase) {
            (itemView as TextView).text = item.name
            mItem = item
        }
    }

    private class TestCase(val name: String, val callback: () -> Unit)
}