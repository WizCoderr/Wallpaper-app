package com.flaxstudio.wallpaperapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flaxstudio.wallpaperapp.source.api.RetrofitClient
import com.flaxstudio.wallpaperapp.source.database.CategoryRepo
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import com.flaxstudio.wallpaperapp.source.database.WallpaperRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(private val wallpaperRepo: WallpaperRepo,private val categoryRepo: CategoryRepo):ViewModel() {

     fun insertWallpapers(wallpaperData: List<WallpaperData>){
      wallpaperRepo.insertWallpaper(wallpaperData)
    }

     private fun fetchCategories(){
         RetrofitClient.wallpaperApi.getWallpaperCategoryData().enqueue(object : Callback<List<WallpaperCategoryData>> {
             override fun onResponse(
                 call: Call<List<WallpaperCategoryData>>,
                 response: Response<List<WallpaperCategoryData>>
             ) {
                 if (response.isSuccessful){
                     viewModelScope.launch {
                         response.body()?.let { categoryRepo.insertData(it) }
                     }
                     Log.d("TAG", "onResponse: Data Added")
                 }
             }
             override fun onFailure(call: Call<List<WallpaperCategoryData>>, t: Throwable) {
                 Log.d("TAG", "onFailure: Error ${t.message}")
             }
         })
     }

    fun getAllCategories():Flow<List<WallpaperCategoryData>> {
        viewModelScope.launch {
            fetchCategories()
        }
        return categoryRepo.getAllCategory()
    }
    private fun getWallpapers(categoryId: String) {
        for (page in 1..80) {
            RetrofitClient.wallpaperApi.getWallpapersPerWallpaper(categoryId, page).enqueue(object : Callback<List<WallpaperData>> {
                override fun onResponse(call: Call<List<WallpaperData>>, response: Response<List<WallpaperData>>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result.isNullOrEmpty()) {
                            // No wallpapers found for this page
                            return
                        }

                       viewModelScope.launch {
                           wallpaperRepo.insertWallpaper(result)
                       }

                        if (page == 80) {
                            // All pages have been loaded
                            // Perform any final actions or UI updates
                            return
                        }
                    } else {
                        // Handle API call failure
                    }
                }

                override fun onFailure(call: Call<List<WallpaperData>>, t: Throwable) {
                    // Handle API call failure
                }
            })
        }
    }


     fun getAllWallpapers():Flow<List<WallpaperData>> = wallpaperRepo.getAllWallpaper()
     fun getWallpapersCategorised(categoryId:String):Flow<List<WallpaperData>> {
         viewModelScope.launch {
             getWallpapers(categoryId)
         }
         return wallpaperRepo.getWallpapersCategorised(categoryId)
     }
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