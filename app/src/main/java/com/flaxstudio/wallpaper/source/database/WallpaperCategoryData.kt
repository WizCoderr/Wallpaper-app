package com.flaxstudio.wallpaper.source.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
     fun getAllCategory(): Flow<List<WallpaperCategoryData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertData(categoryData: List<WallpaperCategoryData>)
}

class CategoryRepo(private val categoryDao: CategoryDao){
     fun insertData(categoryData: List<WallpaperCategoryData>) {
        categoryDao.insertData(categoryData)
    }
     fun getAllCategory():Flow<List<WallpaperCategoryData>> = categoryDao.getAllCategory()
}