package com.flaxstudio.wallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flaxstudio.wallpaper.fragments.GalleryFragment
import com.flaxstudio.wallpaper.fragments.HomeFragmentAll
import com.flaxstudio.wallpaper.utils.FragmentType


class HomePagerAdapter(fragment : Fragment,private val list: List<String>)  : FragmentStateAdapter( fragment){
    override fun getItemCount(): Int {
       return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragmentAll()
            1-> GalleryFragment(FragmentType.Nature)
            2-> GalleryFragment(FragmentType.Space)
            else -> GalleryFragment(FragmentType.Animals)
        }
    }
}