package com.flaxstudio.wallpaper.source.database

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
    val isLiked:Boolean = false,
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


     @Query("Delete From wallpapers WHERE isLiked = 1")
     fun deleteLiked(wallpaperData: WallpaperData)
     @Query("UPDATE wallpapers SET isLiked = :isLiked WHERE _id = :_id")
     fun setLike(isLiked: Boolean,_id: String)

     @Query("SELECT * FROM wallpapers WHERE isLiked = 1")
     fun getAllLikedWallpaper():Flow<List<WallpaperData>>
}
class WallpaperRepo(private val wallpaperDao: WallpaperDao){
    @WorkerThread
     suspend fun insertWallpaper(wallpaperData: List<WallpaperData>){
        wallpaperDao.insertWallpaper(wallpaperData)
    }
    @WorkerThread
     suspend fun getAllWallpaper():Flow<List<WallpaperData>> = wallpaperDao.getAllWallpapers()
    @WorkerThread
    suspend fun getWallpapersCategorised(category_id: String):Flow<List<WallpaperData>> = wallpaperDao.getWallpapersCategorised(category_id)

    @WorkerThread
    suspend fun setLike(wallpaperData: WallpaperData){
        wallpaperDao.setLike(wallpaperData.isLiked,wallpaperData._id)
    }
    @WorkerThread
    suspend fun getAllLikedWallpaper() = wallpaperDao.getAllLikedWallpaper()

    @WorkerThread
    suspend fun deleteLikedWallpaper(wallpaperData: WallpaperData){
        wallpaperDao.deleteLiked(wallpaperData)
    }
}