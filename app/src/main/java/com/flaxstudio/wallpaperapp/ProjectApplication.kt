package com.flaxstudio.wallpaperapp

import android.app.Application
import com.flaxstudio.wallpaperapp.source.db.WallpaperDatabase
import com.flaxstudio.wallpaperapp.source.db.WallpaperRepository

class ProjectApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { WallpaperDatabase.getInstance(this) }
    val projectRepository by lazy { WallpaperRepository(database.wallpaperDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}