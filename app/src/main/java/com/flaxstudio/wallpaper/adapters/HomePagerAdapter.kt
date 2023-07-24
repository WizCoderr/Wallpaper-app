package com.flaxstudio.wallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flaxstudio.wallpaper.fragments.GalleryFragment
import com.flaxstudio.wallpaper.fragments.HomeFragmentAll
import com.flaxstudio.wallpaper.utils.Gallery

class HomePagerAdapter(fragment : Fragment,private val list: List<Gallery>)  : FragmentStateAdapter( fragment){
    override fun getItemCount(): Int {
       return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1->{
                GalleryFragment(Gallery("Nature","1319040"))
            }
            2->{
                GalleryFragment(Gallery("Space","4332580"))
            }
            3->{
                GalleryFragment(Gallery("Animal","8613876"))
            }
            else -> {HomeFragmentAll()}
        }
    }
}