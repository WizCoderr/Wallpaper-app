package com.flaxstudio.wallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flaxstudio.wallpaper.fragments.GalleryFragment
import com.flaxstudio.wallpaper.fragments.HomeFragmentAll

class HomePagerAdapter(fragment : Fragment,private val list: List<String>)  : FragmentStateAdapter( fragment){
    override fun getItemCount(): Int {
       return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position > 0){
            GalleryFragment()
        }else{
            HomeFragmentAll()
        }
    }
}