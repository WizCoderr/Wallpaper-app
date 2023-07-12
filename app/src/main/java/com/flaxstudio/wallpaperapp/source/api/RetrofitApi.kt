package com.flaxstudio.wallpaperapp.source.api

import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WallpaperApi {
    @GET("/public/category")
     fun getWallpaperCategoryData(): Call<List<WallpaperCategoryData>>

    @GET("/public/wallpapers")
    fun getWallpapersPerWallpaper(
        @Query("id") categoryId: String?,
        @Query("page") pageNo: Int
    ): Call<List<WallpaperData>>

    @GET("/public/wallpaper/{wallpaperId}")
    fun getWallpaperFromSearch(@Path("wallpaperId") wallpaperId: String): Call<WallpaperData>

}

object RetrofitClient {
    private const val BASE_URL = "https://wallpaperappbackend-1--wallpaper072.repl.co/"
    private var retrofit: Retrofit? = null
    val wallpaperApi: WallpaperApi
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(WallpaperApi::class.java)
        }
}