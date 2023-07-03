package com.flaxstudio.wallpaperapp.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DownloadFinishReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context?, intent : Intent?) {

        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID , -1L)
            if (id != -1L){
                Log.i("DownloadFinishReceiver" , "download finished with $id id")
                Toast.makeText(context , "Wallpaper Downloaded" , Toast.LENGTH_SHORT).show()
            }

        }

    }
}