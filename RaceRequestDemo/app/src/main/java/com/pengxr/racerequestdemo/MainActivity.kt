package com.pengxr.racerequestdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val mViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenResumed {
            mViewModel.mBooks.collect { books: BooksEntry? ->
                updateList(books)
            }
        }

        findViewById<CheckBox>(R.id.box_request).setOnCheckedChangeListener { buttonView, isChecked -> mViewModel.enableFilterRequest(isChecked) }

        findViewById<CheckBox>(R.id.box_response).setOnCheckedChangeListener { buttonView, isChecked -> mViewModel.enableFilterResponse(isChecked) }
    }

    private fun updateList(books: BooksEntry?) {
        books?.let {
            findViewById<TextView>(R.id.tv_result).text = "结果展示：${it.type}"
        }
    }

    // 串行请求：先请求热门再请求推荐
    fun onClickSync(view: View) {
        mViewModel.onClickSync(this)
    }

    // 并行请求：同时请求热门和推荐
    fun onClickAsync(view: View) {
        mViewModel.onClickAsync(this)
    }

    // 请求热门图书
    fun onClickHot(view: View) {
        mViewModel.onClickHot(this)
    }

    // 请求推荐图书
    fun onClickRecommend(view: View) {
        mViewModel.onClickRecommend(this)
    }
}