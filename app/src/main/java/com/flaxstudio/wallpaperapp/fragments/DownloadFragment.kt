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
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentDownloadBinding
import com.flaxstudio.wallpaperapp.utils.setWallpaperForHomeScreen
import com.flaxstudio.wallpaperapp.utils.setWallpaperForLockScreen
import com.google.android.material.button.MaterialButton


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding
    private lateinit var bitmap: Bitmap
    private lateinit var thisContext: Context
    private lateinit var data:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = requireContext()
        data = requireArguments().getString("image").toString()
        Log.i("TAG", "onViewCreated: $data")
        Glide.with(thisContext).load(data).into(binding.wallpaper)
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
        binding.setWallpaper.setOnClickListener {
            bitmap = binding.wallpaper.drawable.toBitmap()
            setAsWallpaper(bitmap)
        }
        binding.btnDownload.setOnClickListener {
            Toast.makeText(thisContext, "Downloading...", Toast.LENGTH_SHORT).show()
            downloadImage(thisContext, data)
        }

    }

    private fun setAsWallpaper(bitmap: Bitmap) {
        val dialogView =
            LayoutInflater.from(thisContext).inflate(R.layout.wallpaper_set_layout, null)
        val builder = AlertDialog.Builder(thisContext)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.statusBarColor = Color.TRANSPARENT
        dialog.show()
        val radioButton = dialog.findViewById<RadioGroup>(R.id.group_btn)
        dialog.findViewById<MaterialButton>(R.id.apply_btn)?.setOnClickListener {
            val checkedRadioButtonId = radioButton?.checkedRadioButtonId
            if (checkedRadioButtonId == R.id.both_wallpaper) {
                WallpaperManager.getInstance(thisContext).setBitmap(bitmap)
                Toast.makeText(context,"Wallpaper set Successfully",Toast.LENGTH_LONG).show()
                dialog.dismiss()

            } else if (checkedRadioButtonId == R.id.homeScreen) {
                setWallpaperForHomeScreen(thisContext, bitmap)
                dialog.dismiss()

            } else if (checkedRadioButtonId == R.id.lockScreen) {
                setWallpaperForLockScreen(thisContext, bitmap)
                dialog.dismiss()

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