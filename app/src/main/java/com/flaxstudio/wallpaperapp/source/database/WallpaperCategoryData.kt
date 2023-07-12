package com.flaxstudio.wallpaperapp.source.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "wallpaperCategory")
data class WallpaperCategoryData(
    @PrimaryKey val _id: String,
    val title: String,
    val cover_photo: String,
    val blur_hash: String
)

@Dao
interface CategoryDao{
    @Query("SELECT * FROM wallpaperCategory")
    suspend fun getAllCategory(): Flow<List<WallpaperCategoryData>>

    @Insert
    suspend fun insertData(categoryData: WallpaperCategoryData)
}

class CategoryRepo(private val categoryDao: CategoryDao){
    suspend fun insertData(categoryData: WallpaperCategoryData) {
        categoryDao.insertData(categoryData)
    }
    suspend fun getAllCategory():Flow<List<WallpaperCategoryData>> = categoryDao.getAllCategory()
}