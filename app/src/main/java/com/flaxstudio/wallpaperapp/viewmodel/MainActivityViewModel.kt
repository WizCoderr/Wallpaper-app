package com.flaxstudio.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flaxstudio.wallpaperapp.source.db.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.db.WallpaperData
import com.flaxstudio.wallpaperapp.source.db.WallpaperRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val wallpaperRepository: WallpaperRepository):ViewModel() {

    // ------------------------- Room Project Handler Code ------------------------------------
    suspend fun getAllWallpapers(): Flow<List<WallpaperData>> {
        return wallpaperRepository.getAllWallpapers()
    }
    suspend fun getWallpaperFromCategory(categoryId:String): Flow<List<WallpaperData>>{
        return wallpaperRepository.getWallpapersByCategory(categoryId)
    }
    fun insertCategory(categoryData: WallpaperCategoryData) = viewModelScope.launch(Dispatchers.Default) {
        wallpaperRepository.insertCategory(categoryData)
    }
    fun insertWallpaper(wallpaperData: WallpaperData) = viewModelScope.launch(Dispatchers.Default) {
        wallpaperRepository.insertWallpaper(wallpaperData)
    }
}
class MainActivityViewModelFactory(private val projectRepository: WallpaperRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(projectRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}