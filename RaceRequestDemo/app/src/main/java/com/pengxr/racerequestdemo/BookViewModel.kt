package com.pengxr.racerequestdemo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by pengxr on 9/8/2022
 */
class BookViewModel : ViewModel() {

    private val mModel = BookModel()

    val mBooks = MutableSharedFlow<BooksEntry?>()

    // 过滤过期响应开关
    private var filterResponseEnabled = false

    // 取消过期请求开关
    private var filterRequestEnabled = false

    // 最新状态标识
    private var mSelectedType: String = ""

    // 串行请求：先请求热门再请求推荐
    fun onClickSync(context: Context) {
        viewModelScope.launch {
            val hot_books = mModel.fetchBooks(context, "热门图书", filterRequestEnabled)
            mBooks.emit(hot_books)
            val recommend_books = mModel.fetchBooks(context, "推荐图书", filterRequestEnabled)
            mBooks.emit(recommend_books)
        }
    }

    // 并行请求：同时请求热门和推荐
    fun onClickAsync(context: Context) {
        onClickHot(context)
        onClickRecommend(context)
    }

    // 请求热门图书
    fun onClickHot(context: Context) {
        viewModelScope.launch {
            mSelectedType = "热门图书"
            val books = mModel.fetchBooks(context, mSelectedType, filterRequestEnabled)
            // 忽略过期响应
            if (filterResponseEnabled && mSelectedType != books?.type) {
                Toast.makeText(context, "一次响应被过滤", Toast.LENGTH_SHORT).show()
                return@launch
            }
            // 返回
            mBooks.emit(books)
        }
    }

    // 请求推荐图书
    fun onClickRecommend(context: Context) {
        viewModelScope.launch {
            mSelectedType = "推荐图书"
            val books = mModel.fetchBooks(context, mSelectedType, filterRequestEnabled)
            // 忽略过期响应
            if (filterResponseEnabled && mSelectedType != books?.type) {
                Toast.makeText(context, "一次响应被过滤", Toast.LENGTH_SHORT).show()
                return@launch
            }
            // 返回
            mBooks.emit(books)
        }
    }

    fun enableFilterResponse(enable: Boolean) {
        filterResponseEnabled = enable
    }

    fun enableFilterRequest(enable: Boolean) {
        filterRequestEnabled = enable
    }
}