package com.pengxr.racerequestdemo

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.EventListener
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by pengxr on 9/8/2022
 */
object RetrofitHolder {

    /**
     * 全局 OkHttpClient 对象
     */
    val client by lazy {
        // 信任所有证书
        val trustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        // 请求监听
        val eventListener = object : EventListener() {

            override fun callStart(call: okhttp3.Call) {
                super.callStart(call)
                Log.d("XIAOPENG", "请求开始：${call.request().url}")
            }

            override fun callEnd(call: okhttp3.Call) {
                super.callEnd(call)
                Log.d("XIAOPENG", "请求结束：${call.request().url}")
            }

            override fun callFailed(call: okhttp3.Call, ioe: IOException) {
                super.callFailed(call, ioe)
                Log.d("XIAOPENG", "请求失败：${call.request().url}")
            }
        }
        OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .eventListener(eventListener)
            .build()
    }

    /**
     * 全局 Retrofit 对象
     */
    val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://my-json-server.typicode.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            .build()
    }

    fun cancelCallWithTag(context: Context, tag: String) {
        for (call in client.dispatcher.queuedCalls()) {
            if (call.request().tag(String::class.java) == tag) {
                Toast.makeText(context, "一次请求被取消", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        }
        for (call in client.dispatcher.runningCalls()) {
            if (call.request().tag(String::class.java) == tag) {
                Toast.makeText(context, "一次请求被取消", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        }
    }
}