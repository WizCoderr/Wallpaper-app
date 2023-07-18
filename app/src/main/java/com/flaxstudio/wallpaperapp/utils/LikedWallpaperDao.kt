package com.flaxstudio.wallpaperapp.utils

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LikedWallpaperDao {

    private val db = FirebaseFirestore.getInstance()
    private val wallpaperCollection = db.collection("likedWallpaper")

    fun addWallpaper(uid : String, imageUrl : String, blurHash : String) {

        GlobalScope.launch(Dispatchers.IO) {
            val wallpaper : LikedWallpapers = LikedWallpapers(uid , imageUrl , blurHash)
            wallpaperCollection.document().set(wallpaper)
        }
    }

}