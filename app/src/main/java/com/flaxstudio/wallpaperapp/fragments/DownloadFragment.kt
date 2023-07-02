package com.flaxstudio.wallpaperapp.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flaxstudio.wallpaperapp.databinding.FragmentDownloadBinding


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding
    private lateinit var bitmap:Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDownloadBinding.inflate(inflater , container , false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bitmap = (binding.wallpaper.drawable as BitmapDrawable).bitmap
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
        binding.setWallpaper.setOnClickListener { setAsWallpaper(bitmap) }
        binding.btnDownload.setOnClickListener{
            downloadImage(requireContext() , "https://images.unsplash.com/photo-1655705247374-02e5e9a2bbec")
        }

    }

    private fun setAsWallpaper(bitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(requireContext())
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK)
        else
            wallpaperManager.setBitmap(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun downloadImage(context: Context, url: String): Long {
        val downloadManager = context.getSystemService(DownloadManager::class.java)!!

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpg")
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