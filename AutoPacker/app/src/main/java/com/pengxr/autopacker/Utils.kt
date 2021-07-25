package com.pengxr.autopacker

import android.content.Context
import android.content.pm.PackageManager


/**
 * Created by pengxr on 25/7/2021
 */

/**
 * 分发渠道号
 */
fun getChannel(context: Context): String {
    val pm: PackageManager = context.packageManager
    val appInfo = pm.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
    return appInfo.metaData.getString("channel_id") ?: ""
}