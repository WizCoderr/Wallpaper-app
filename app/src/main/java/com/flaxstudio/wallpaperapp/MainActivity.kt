package com.flaxstudio.wallpaperapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flaxstudio.wallpaperapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}