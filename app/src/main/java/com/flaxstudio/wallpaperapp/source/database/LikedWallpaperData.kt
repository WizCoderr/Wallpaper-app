package com.flaxstudio.wallpaperapp.source.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "Liked_wallpaper")
data class LikedWallpaper(
    @PrimaryKey(autoGenerate = false) val _id: Int,
    val image_url: String,
    val blur_hash: String,
    val is_premium: Boolean,
    val time : Long,
    val uid : String
)

@Dao
interface LikedWallpaperDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWallpaper(likedWallpaper: LikedWallpaper)

    @Delete
    fun deleteWallpaper(likedWallpaper: LikedWallpaper)


    @Query("SELECT * FROM Liked_wallpaper ORDER BY time ASC")
    fun getAllLikedWallpaper() : Flow<List<LikedWallpaper>>

    @Insert
    fun addAllLikedWallpaper(wallpapers : List<LikedWallpaper>)

    @Query("DELETE FROM Liked_wallpaper")
    fun clearTable()


}

class LikedWallpaperRepo(private val likedWallpaperDao: LikedWallpaperDao){

    @WorkerThread
    fun addWallpaper(likedWallpaper: LikedWallpaper){
        likedWallpaperDao.addWallpaper(likedWallpaper)
    }

    @WorkerThread
    fun deleteWallpaper(likedWallpaper: LikedWallpaper){
        likedWallpaperDao.deleteWallpaper(likedWallpaper)
    }

    @WorkerThread
    fun getAllLikedWallpaper() : Flow<List<LikedWallpaper>> {
        return likedWallpaperDao.getAllLikedWallpaper()
    }

    @WorkerThread
    fun addAllLikedWallpaper(allLikedWallpaper : List<LikedWallpaper>){
        likedWallpaperDao.addAllLikedWallpaper(allLikedWallpaper)
    }

    @WorkerThread
    fun clearTable(){
        likedWallpaperDao.clearTable()
    }

}

