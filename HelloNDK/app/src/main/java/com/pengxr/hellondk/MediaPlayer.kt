package com.pengxr.hellondk

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by pengxr on 11/7/2022
 */
class MediaPlayer(lifecycleOwner: LifecycleOwner) : LifecycleEventObserver {

    companion object {
        init {
            System.loadLibrary("hellondk")
        }
    }

    private var nativeObj: Long? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    // ------------------------------------------------------------------------------------------
    // public
    // ------------------------------------------------------------------------------------------

    var videoPath: String? = null

    fun restart() {
        videoPath?.let {
            nativeObj = prepareNative(it)
        }
    }

    fun stop() {

    }

    // ------------------------------------------------------------------------------------------
    // LifecycleEventObserver
    // ------------------------------------------------------------------------------------------

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
//            Lifecycle.Event.ON_RESUME -> {
//
//            }
            Lifecycle.Event.ON_STOP -> {
                nativeObj?.let {
                    stopNative(it)
                }
            }
            Lifecycle.Event.ON_DESTROY -> {
                nativeObj?.let {
                    releaseNative(it)
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------
    // Native
    // ------------------------------------------------------------------------------------------

    private external fun prepareNative(path: String): Long
    private external fun startNative(nativeObj: Long)
    private external fun stopNative(nativeObj: Long)
    private external fun releaseNative(nativeObj: Long)

    fun onPrepared() {
        nativeObj?.let {
            Log.i("XIAOPENG", "prepared")
            startNative(it)
        }
    }
}