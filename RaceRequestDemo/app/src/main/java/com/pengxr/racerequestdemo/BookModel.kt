package com.pengxr.racerequestdemo

import android.content.Context

/**
 * Created by pengxr on 9/8/2022
 */
class BookModel {

    companion object {
        const val CALL_TAG = "BOOKS"
    }

    suspend fun fetchBooks(context: Context, type: String?, filterRequestEnabled: Boolean): BooksEntry? {
        if (filterRequestEnabled) {
            RetrofitHolder.cancelCallWithTag(context, CALL_TAG)
        }
        return try {
            val api: BookApi = RetrofitHolder.retrofit.create(BookApi::class.java)
            val list = api.fetchBooks(type, CALL_TAG)
            // 由于服务端接口没有提供 type 类型，所以需要自己包装一层
            BooksEntry(type, list)
        } catch (ex: Exception) {
            null
        }
    }
}