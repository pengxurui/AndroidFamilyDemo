package com.pengxr.hellobuildvariant

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pengxr.hellobuildvariant.variant.PayHelper


class MainActivity : AppCompatActivity() {

    private val TAG = "XIAOPENG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.i(TAG, "应用版本：${PayHelper().func()}")

        Log.i(TAG, "APP_ID：${getAppId(this)}")

        Log.i(TAG, "label：${resources.getString(R.string.label)}")
    }

    // 获取 MetaData APP_ID
    private fun getAppId(context: Context): String {
        return try {
            val applicationInfo: ApplicationInfo = context.packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
            // 纯整数用 getString 会返回空
            applicationInfo.metaData.getString("APP_ID")!!.substring(2)
        } catch (e: Exception) {
            // ignore
            ""
        }
    }
}