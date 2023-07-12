package com.flaxstudio.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import com.flaxstudio.wallpaperapp.source.database.CategoryRepo
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import com.flaxstudio.wallpaperapp.source.database.WallpaperRepo
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(private val wallpaperRepo: WallpaperRepo,private val categoryRepo: CategoryRepo):ViewModel() {

    suspend fun insertWallpapers(wallpaperData: WallpaperData){
      wallpaperRepo.insertWallpaper(wallpaperData)
    }

    suspend fun insertCategory(categoryData: WallpaperCategoryData){
        categoryRepo.insertData(categoryData)
    }

    suspend fun getAllWallpapers():Flow<List<WallpaperData>> = wallpaperRepo.getAllWallpaper()
    suspend fun getWallpapersCategorised(categoryId:String):Flow<List<WallpaperData>> = wallpaperRepo.getWallpapersCategorised(categoryId)
}