package com.flaxstudio.wallpaperapp.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WallpaperCategoryData::class, WallpaperData::class], version = 1)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao
    abstract fun categoryDao(): CategoryDao
    companion object {
        // Singleton instance of the database
        @Volatile
        private var INSTANCE: WallpaperDatabase? = null

        fun getInstance(context: Context): WallpaperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WallpaperDatabase::class.java,
                    "wallpaper_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}