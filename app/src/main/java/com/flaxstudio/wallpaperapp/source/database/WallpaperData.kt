package com.flaxstudio.wallpaperapp.source.database

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "WALLPAPERS", foreignKeys = [
    ForeignKey(
        entity = WallpaperCategoryData::class,
        parentColumns = ["_id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE,

    )
])
data class WallpaperData(
    @PrimaryKey val _id: String,
    val category_id: String,
    val created_at: Long,
    val width: Long,
    val height: Long,
    val color: String,
    val blur_hash: String,
    val description: String,
    val image_url: String,
    val likes: Long,
    val is_premium: Boolean
)

@Dao
interface WallpaperDao{
    @Query("SELECT * FROM WALLPAPERS")
     fun getAllWallpapers():Flow<List<WallpaperData>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertWallpaper(wallpaperData: List<WallpaperData>)

    @Query("SELECT * FROM WALLPAPERS WHERE category_id = :category_id")
     fun getWallpapersCategorised(category_id: String):Flow<List<WallpaperData>>
}
class WallpaperRepo(private val wallpaperDao: WallpaperDao){
    @WorkerThread
     fun insertWallpaper(wallpaperData: List<WallpaperData>){
        wallpaperDao.insertWallpaper(wallpaperData)
    }
    @WorkerThread
     fun getAllWallpaper():Flow<List<WallpaperData>> = wallpaperDao.getAllWallpapers()
    @WorkerThread
    fun getWallpapersCategorised(category_id: String):Flow<List<WallpaperData>> = wallpaperDao.getWallpapersCategorised(category_id)
}