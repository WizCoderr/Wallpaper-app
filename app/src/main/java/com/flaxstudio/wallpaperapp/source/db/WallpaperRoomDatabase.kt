package com.flaxstudio.wallpaperapp.source.db

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "categories")
data class WallpaperCategoryData(
    @PrimaryKey val _id: String,
    val title: String,
    val cover_photo: String,
    val blur_hash: String
)

@Entity(tableName = "wallpaper", foreignKeys = [
    ForeignKey(
        entity = WallpaperCategoryData::class,
        parentColumns = ["_id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class WallpaperData(
    @PrimaryKey val _id: String,
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


@Dao
interface WallpaperDao {
    // WallpaperCategoryData queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: WallpaperCategoryData)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): Flow<List<WallpaperCategoryData>>

    // WallpaperData queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpaper(wallpaper: WallpaperData)

    @Query("SELECT * FROM wallpaper")
    suspend fun getAllWallpapers():Flow<List<WallpaperData>>

    @Query("SELECT * FROM wallpaper WHERE category_id = :categoryId")
    suspend fun getWallpapersByCategory(categoryId: String): Flow<List<WallpaperData>>
}

class WallpaperRepository(private val wallpaperDao: WallpaperDao) {
    @WorkerThread
    suspend fun insertCategory(category: WallpaperCategoryData){
        wallpaperDao.insertCategory(category)
    }
    @WorkerThread
    suspend fun getAllCategories(): Flow<List<WallpaperCategoryData>>{
        return wallpaperDao.getAllCategories()
    }
    @WorkerThread
    suspend fun insertWallpaper(wallpaper: WallpaperData){
        wallpaperDao.insertWallpaper(wallpaper)
    }

    @WorkerThread
    suspend fun getWallpapersByCategory(categoryId: String): Flow<List<WallpaperData>> {
        return wallpaperDao.getWallpapersByCategory(categoryId)
    }
    @WorkerThread
    suspend fun getAllWallpapers(): Flow<List<WallpaperData>>{
       return wallpaperDao.getAllWallpapers()
    }
}