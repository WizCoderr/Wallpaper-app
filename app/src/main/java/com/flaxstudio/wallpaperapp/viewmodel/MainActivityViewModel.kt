package com.flaxstudio.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flaxstudio.wallpaperapp.source.database.CategoryRepo
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import com.flaxstudio.wallpaperapp.source.database.WallpaperRepo
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(private val wallpaperRepo: WallpaperRepo,private val categoryRepo: CategoryRepo):ViewModel() {

     fun insertWallpapers(wallpaperData: WallpaperData){
      wallpaperRepo.insertWallpaper(wallpaperData)
    }

     fun insertCategory(categoryData: List<WallpaperCategoryData>){
        categoryRepo.insertData(categoryData)
    }

    fun getAllCategories():Flow<List<WallpaperCategoryData>> = categoryRepo.getAllCategory()

     fun getAllWallpapers():Flow<List<WallpaperData>> = wallpaperRepo.getAllWallpaper()
     fun getWallpapersCategorised(categoryId:String):Flow<List<WallpaperData>> = wallpaperRepo.getWallpapersCategorised(categoryId)
}

// this will allow us to pass parameter to view model
class MainActivityViewModelFactory(private val wallpaperRepo: WallpaperRepo, private val categoryRepo: CategoryRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(wallpaperRepo, categoryRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}