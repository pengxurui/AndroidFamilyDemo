package com.pengxr.racerequestdemo

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Tag

/**
 * Created by pengxr on 9/8/2022
 */
interface BookApi {

    /**
     * 获取图书列表（假接口，用 Charles 拦截请求）
     *
     * @param type 图书类型，为空表全部
     */
    @GET("/pengxurui/FakeServer/posts")
    suspend fun fetchBooks(@Query("type") type: String?, @Tag tag: String): List<BooksEntry.Book>
}