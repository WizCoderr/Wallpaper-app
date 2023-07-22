package com.flaxstudio.wallpaper.utils

import android.util.Log
import com.flaxstudio.wallpaper.source.database.LikedWallpaper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseLikedWallpaperDao {

    private val db = FirebaseFirestore.getInstance()
    private val wallpaperCollection = db.collection("likedWallpaper")

    fun addWallpaper(likedWallpaper: LikedWallpaper) {
        GlobalScope.launch(Dispatchers.IO) {
            wallpaperCollection.document().set(likedWallpaper)
        }
    }

    fun getAllWallpaper(uid : String) : List<LikedWallpaper>{

        val likedWallpaperList = arrayListOf<LikedWallpaper>()
        GlobalScope.launch {
            wallpaperCollection.whereEqualTo("uid" , uid).get()
                .addOnSuccessListener { documents ->


                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        val wallpaper = document.toObject(LikedWallpaper::class.java)
                        likedWallpaperList.add(wallpaper)
                    }


                }
                .addOnFailureListener{ exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
        }

        return likedWallpaperList
    }

}