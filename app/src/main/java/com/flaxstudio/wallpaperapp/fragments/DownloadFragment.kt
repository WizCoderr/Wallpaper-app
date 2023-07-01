package com.flaxstudio.wallpaperapp.fragments

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding
import com.flaxstudio.wallpaperapp.databinding.FragmentDownloadBinding


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding


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
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }

        binding.btnLike.setOnClickListener{
            downloadImage(requireContext() , "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg")
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun downloadImage(context : Context, url : String )  {
        val downloadManager = context.getSystemService(DownloadManager::class.java)!!

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("wallpaper.jpg")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , "wallpaper.jpg")
        var reference : Long =  downloadManager.enqueue(request)

       // return downloadManager.enqueue(request)
    }

}