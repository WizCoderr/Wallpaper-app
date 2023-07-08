package com.flaxstudio.wallpaperapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flaxstudio.wallpaperapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.home -> {
                   // navigateToFragment(HomeFragment())
                    true
                }
                R.id.favourites -> {
                  //  navigateToFragment(CollectionFragment())
                    true
                }
                R.id.account ->{
                   // navigateToFragment(AccountFragment())
                    true
                }
                else -> false
            }
        }


    }


    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()

    }
}