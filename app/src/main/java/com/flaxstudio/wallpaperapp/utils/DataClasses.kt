package com.flaxstudio.wallpaperapp.utils

data class DownloadItems(
     val img:Int,
     val txt:String
)

data class Users (val displayName : String = "", val imageUrl : String = "", val uid : String = "" ,val subscriber : Boolean = false , val startDate : Long = 0L , val endDate : Long = 0L)

data class LikedWallpapers(val uid : String , val imageUrl: String , val blurHash : String)