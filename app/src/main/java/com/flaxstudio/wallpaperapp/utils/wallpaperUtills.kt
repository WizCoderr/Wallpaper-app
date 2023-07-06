package com.flaxstudio.wallpaperapp.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast

fun setWallpaperForHomeScreen(context: Context, bitmap: Bitmap){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
        WallpaperManager.getInstance(context).setBitmap(bitmap, null,true,WallpaperManager.FLAG_SYSTEM)
    }else
        WallpaperManager.getInstance(context).setBitmap(bitmap)
    Toast.makeText(context,"Wallpaper set Successfully to Home Screen", Toast.LENGTH_LONG).show()

}
fun setWallpaperForLockScreen(context: Context, bitmap: Bitmap){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
        WallpaperManager.getInstance(context).setBitmap(bitmap, null,true,WallpaperManager.FLAG_LOCK)
    }else
        WallpaperManager.getInstance(context).setBitmap(bitmap)
    Toast.makeText(context,"Wallpaper set Successfully to Lock Screen",Toast.LENGTH_LONG).show()

}