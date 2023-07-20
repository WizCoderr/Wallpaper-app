package com.flaxstudio.wallpaperapp.utils

import android.util.Log
import com.flaxstudio.wallpaperapp.source.database.LikedWallpaper
import com.google.firebase.firestore.FirebaseFirestore
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

    fun getAllWallpaper(uid : String){
        GlobalScope.launch {
            wallpaperCollection.whereEqualTo("uid" , uid).get()
                .addOnSuccessListener { documents ->

                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }


                }
                .addOnFailureListener{ exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
        }
    }

}