package com.flaxstudio.wallpaperapp.utils

class DataClasses {



}

data class WallpaperCategoryData(
    val _id: String,
    val title: String,
    val cover_photo: String,
    val blur_hash: String
)

data class WallpaperData(
    val _id: String,
    val category_id: String,
    val created_at: Int,
    val width: Int,
    val height: Int,
    val color: String,
    val blur_hash: String,
    val description: String,
    val image_url: String,
    val likes: Int,
    val is_premium: Boolean
)

enum class FragmentType{
    All,
    Specific
}