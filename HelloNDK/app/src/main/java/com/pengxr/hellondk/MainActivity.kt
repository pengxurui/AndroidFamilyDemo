package com.pengxr.hellondk

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pengxr.hellondk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val selectorVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        val path = uri?.let {
            PictureUtil.getPath(this, it)
        }
        path?.let {
            LocalPlayerActivity.player(this, it)
        }
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGrant ->
        if (isGrant) {
            selectorVideoLauncher.launch("video/*")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onSelectVideo(view: View) {
        requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}