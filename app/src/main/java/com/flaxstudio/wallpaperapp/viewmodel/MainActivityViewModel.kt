package com.flaxstudio.wallpaperapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flaxstudio.wallpaperapp.source.api.RetrofitClient
import com.flaxstudio.wallpaperapp.source.database.CategoryRepo
import com.flaxstudio.wallpaperapp.source.database.LikedWallpaper
import com.flaxstudio.wallpaperapp.source.database.LikedWallpaperRepo
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import com.flaxstudio.wallpaperapp.source.database.WallpaperRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(private val wallpaperRepo: WallpaperRepo,private val categoryRepo: CategoryRepo , private val likedWallpaperRepo: LikedWallpaperRepo):ViewModel() {

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
        val totalPages = 80
        val batchSize = 10 // Number of API calls to be made in parallel

        val scope = CoroutineScope(Dispatchers.IO)
        val deferredResults = mutableListOf<Deferred<Response<List<WallpaperData>>>>()

        scope.launch {
            for (batch in 1..(totalPages / batchSize)) {
                Log.d("TAG", "getWallpapers: $batch")
                val startPage = (batch - 1) * batchSize + 1
                val endPage = minOf(batch * batchSize, totalPages)

                for (page in startPage..endPage) {
                    val deferred = async {
                        RetrofitClient.wallpaperApi.getWallpapersPerWallpaper(categoryId, page).execute()
                    }
                    deferredResults.add(deferred)
                }

                val responses = deferredResults.awaitAll()

                viewModelScope.launch {
                    responses.forEachIndexed { index, response ->
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (!result.isNullOrEmpty()) {
                                wallpaperRepo.insertWallpaper(result)
                            }
                        } else {
                            // Handle API call failure
                        }
                    }
                }

                deferredResults.clear()
            }
        }
    }


     fun getAllWallpapers():Flow<List<WallpaperData>> = wallpaperRepo.getAllWallpaper()
     fun getWallpapersCategorised(categoryId:String):Flow<List<WallpaperData>> {
         viewModelScope.launch {
             getWallpapers(categoryId)
         }
         return wallpaperRepo.getWallpapersCategorised(categoryId)
     }

    // Liked Wallpaper Operations
    fun insertLikedWallpaper(likedWallpaper: LikedWallpaper) {
        likedWallpaperRepo.addWallpaper(likedWallpaper)
    }
    fun deleteLikedWallpaper(likedWallpaper: LikedWallpaper){
        likedWallpaperRepo.deleteWallpaper(likedWallpaper)
    }
    fun getAllLikedWallpaper() : Flow<List<LikedWallpaper>>{
       return likedWallpaperRepo.getAllLikedWallpaper()
    }
    fun addAllLikedWallpaper(likedWallpaper : List<LikedWallpaper>){
        likedWallpaperRepo.addAllLikedWallpaper(likedWallpaper)
    }
    fun clearTable(){
        likedWallpaperRepo.clearTable()
    }

}

// this will allow us to pass parameter to view model
class MainActivityViewModelFactory(private val wallpaperRepo: WallpaperRepo, private val categoryRepo: CategoryRepo , private val likedWallpaperRepo: LikedWallpaperRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(wallpaperRepo, categoryRepo , likedWallpaperRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}