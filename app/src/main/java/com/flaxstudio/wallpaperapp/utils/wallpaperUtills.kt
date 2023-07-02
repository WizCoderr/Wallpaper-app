package com.flaxstudio.wallpaperapp.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build

fun WallpaperManager.setWallpaper(context: Context,bitmap:Bitmap,flag:Int){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
        WallpaperManager.getInstance(context).setBitmap(bitmap, Rect(),true,flag)
    }else
        WallpaperManager.getInstance(context).setBitmap(bitmap)
}