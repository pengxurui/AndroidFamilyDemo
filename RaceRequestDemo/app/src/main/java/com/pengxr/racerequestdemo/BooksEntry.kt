package com.pengxr.racerequestdemo

import java.io.Serializable

/**
 * Created by pengxr on 9/8/2022
 */
class BooksEntry(
    // 类型
    val type: String? = null,
    // 数据
    val list: List<Book>? = null
) : Serializable {

    class Book : Serializable {
        val id: String? = null
        val name: String? = null
    }
}