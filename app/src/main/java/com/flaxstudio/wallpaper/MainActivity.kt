package com.flaxstudio.wallpaper

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.flaxstudio.wallpaper.source.database.LikedWallpaper
import com.flaxstudio.wallpaper.utils.FirebaseLikedWallpaperDao
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (application as ProjectApplication).wallpaperRepository,
            (application as ProjectApplication).categoryRepository,
            (application as ProjectApplication).likedWallpaperRepository
        )
    }
    private var allLikedWallpapers: List<LikedWallpaper> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch(Dispatchers.Default) {
            mainActivityViewModel.getAllLikedWallpaper().collect {
                allLikedWallpapers = it
                withContext(Dispatchers.Main){
                    update()
                }
            }
        }

        Log.i("TAG", "all wallpaper : $allLikedWallpapers")

    }

    fun update() {
        val dao = FirebaseLikedWallpaperDao()
        for (wallpaper in allLikedWallpapers) {
            dao.addWallpaper(wallpaper)
        }

    }
}