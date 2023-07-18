package com.flaxstudio.wallpaperapp.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.WallpaperAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentDownloadBinding
import com.flaxstudio.wallpaperapp.utils.DownloadItems
import com.flaxstudio.wallpaperapp.utils.LikedWallpaperDao
import com.flaxstudio.wallpaperapp.utils.setWallpaperForHomeScreen
import com.flaxstudio.wallpaperapp.utils.setWallpaperForLockScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding
    private lateinit var bitmap: Bitmap
    private lateinit var thisContext: Context
    private lateinit var data:String
    private lateinit var auth : FirebaseAuth
    private lateinit var currUser : FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = requireContext()
        currUser = auth.currentUser!!
        when(requireArguments().getInt("collect")){
            0-> data = requireArguments().getString("image").toString()
            1-> data = requireArguments().getString("categoryImg").toString()
        }
        Log.i("TAG", "onViewCreated: $data")
        Glide.with(thisContext).load(data).into(binding.wallpaper)
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
        binding.setWallpaper.setOnClickListener {
            bitmap = binding.wallpaper.drawable.toBitmap()
            setAsWallpaper(bitmap)
        }
        binding.btnLike.setOnClickListener {
            //Dummy data
            var uid : String = currUser.uid
            var imageUrl : String = "dfsdfsdfsf"
            var blurHash : String = "sdsdfsdf"
            wallpaperLiked(uid , imageUrl , blurHash)
        }

    }

    private fun wallpaperLiked( uid : String , imageUrl :String , blurHash : String) {
        val wallpaperDao  = LikedWallpaperDao()
        wallpaperDao.addWallpaper(uid , imageUrl , blurHash)
    }

    private fun setAsWallpaper(bitmap: Bitmap) {
        val dialogView =
            LayoutInflater.from(thisContext).inflate(R.layout.wallpaper_set_layout, null)
        val builder = AlertDialog.Builder(thisContext)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window!!.attributes.windowAnimations = R.style.PopupAnimation
        dialog.window?.statusBarColor = Color.TRANSPARENT
        dialog.show()
        val arrayList = listOf(
            DownloadItems(R.drawable.icon_wallpaper,"SET AS WALLPAPER"),
            DownloadItems(R.drawable.icon_both,"SET BOTH"),
            DownloadItems(R.drawable.icon_lock,"SET LOCK SCREEN WALLPAPER"),
            DownloadItems(R.drawable.icon_download,"DOWNLOAD WALLPAPER")
        )
       dialogView.findViewById<ListView>(R.id.listViewWallpaper).adapter = WallpaperAdapter(thisContext,arrayList)
       dialogView.findViewById<ListView>(R.id.listViewWallpaper).setOnItemClickListener { _, _, position, _ ->
           when(arrayList[position]){
               arrayList[0]->{
                   WallpaperManager.getInstance(thisContext).setWallpaperForHomeScreen(thisContext,bitmap)
               }
               arrayList[1]->{
                   WallpaperManager.getInstance(thisContext).setWallpaperForHomeScreen(thisContext,bitmap)
                   WallpaperManager.getInstance(thisContext).setWallpaperForLockScreen(thisContext,bitmap)

               }
               arrayList[2]->{
                   WallpaperManager.getInstance(thisContext).setWallpaperForLockScreen(thisContext,bitmap)

               }
               arrayList[3]->{
                   Toast.makeText(thisContext, "Downloading...", Toast.LENGTH_SHORT).show()
                   downloadImage(thisContext, data)
               }
           }
       }
    }








    private fun downloadImage(context: Context, url: String): Long {
        // val downloadManager = context.getSystemService(DownloadManager::class.java)!!
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(url.toUri()).setMimeType("image/jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("wallpaper.jpg")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "wallpaper.jpg")


        //return downloadManager.enqueue(request)
        try {
            val downloadId = downloadManager.enqueue(request)
            Log.i("DownloadFinishReceiver", "Download enqueued with ID: $downloadId")
            return downloadId
        } catch (e: Exception) {
            Log.e("DownloadFinishReceiver", "Error enqueueing download: ${e.message}")
            e.printStackTrace()
        }

        return -1L
    }

}