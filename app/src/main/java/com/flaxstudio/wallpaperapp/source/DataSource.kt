package com.flaxstudio.wallpaperapp.source

import android.util.Log
import com.flaxstudio.wallpaperapp.source.api.RetrofitClient
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun fetchCategories(mainActivityViewModel: MainActivityViewModel) {
        RetrofitClient.wallpaperApi.getWallpaperCategoryData().enqueue(object : Callback<List<WallpaperCategoryData>> {
        override fun onResponse(
            call: Call<List<WallpaperCategoryData>>,
            response: Response<List<WallpaperCategoryData>>
        ) {
            if (response.isSuccessful) {
                // Process the successful response here
                val categories: List<WallpaperCategoryData>? = response.body()
                Log.d("TAG", "onResponse: $categories")
                if (categories != null) {
                    mainActivityViewModel.insertCategory(categories)
                    Log.d("TAG", "onResponse: Data added to roomDb")
                }

            } else {
                // Handle error response
            }
        }

        override fun onFailure(call: Call<List<WallpaperCategoryData>>, t: Throwable) {
            // Handle network failure
        }
    })
}

