package com.flaxstudio.wallpaperapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentHostBinding

@Suppress("DEPRECATION")
class HostFragment :Fragment() {
    private lateinit var hostBinding: FragmentHostBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        hostBinding = FragmentHostBinding.inflate(layoutInflater)
        return hostBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToFragment(HomeFragment())
        hostBinding.bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.home -> {
                     navigateToFragment(HomeFragment())
                    true
                }
                R.id.collections->{
                    navigateToFragment(AllWallpaperFragment())
                    true
                }
                R.id.favourites -> {
                      navigateToFragment(FavoritesFragment())
                    true
                }
                R.id.account ->{
                     navigateToFragment(AccountFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun navigateToFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.container, fragment)?.commit()

    }
}