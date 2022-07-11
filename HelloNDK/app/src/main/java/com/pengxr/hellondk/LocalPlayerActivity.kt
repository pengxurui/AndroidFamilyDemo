package com.pengxr.hellondk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class LocalPlayerActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_PATH = "LOCAL_PATH"

        fun player(context: Context, path: String) {
            context.startActivity(Intent(context, LocalPlayerActivity::class.java).apply {
                putExtra(EXTRA_PATH, path)
            })
        }
    }

    private val player by lazy {
        MediaPlayer(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_local_player)

        init()
    }

    private fun init() {
        val path = intent.getStringExtra(EXTRA_PATH)

        player.videoPath = path
        player.restart()
    }
}