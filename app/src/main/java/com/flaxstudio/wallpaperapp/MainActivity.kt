package com.flaxstudio.wallpaperapp

import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.flaxstudio.wallpaperapp.databinding.ActivityMainBinding
import com.flaxstudio.wallpaperapp.source.database.LikedWallpaper
import com.flaxstudio.wallpaperapp.utils.FirebaseLikedWallpaperDao
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (application as ProjectApplication).wallpaperRepository,
            (application as ProjectApplication).categoryRepository,
            (application as ProjectApplication).likedWallpaperRepository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val allLikedWallpapers  = mainActivityViewModel.getAllLikedWallpaper() as List<LikedWallpaper>
        Log.i("TAG" , "all wallpaper : $allLikedWallpapers")

        if (allLikedWallpapers != null ){
            val dao = FirebaseLikedWallpaperDao()
            for (wallpaper in allLikedWallpapers){
                dao.addWallpaper(wallpaper)
            }
        }

    }

}