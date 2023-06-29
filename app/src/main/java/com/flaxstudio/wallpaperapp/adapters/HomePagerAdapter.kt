package com.flaxstudio.wallpaperapp.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flaxstudio.wallpaperapp.fragments.HomeFragment
import com.flaxstudio.wallpaperapp.fragments.HomeFragmentAll

class HomePagerAdapter(fragment : Fragment)  : FragmentStateAdapter( fragment){
    override fun getItemCount(): Int {
       return 4
    }

    override fun createFragment(position: Int): Fragment {
      return HomeFragmentAll()
    }
}