package com.flaxstudio.wallpaper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flaxstudio.wallpaper.adapters.HomePagerAdapter
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val tabTitles = arrayListOf("All","Nature" , "Space" , "Animals")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater ,container , false)

        setupLayoutWithViewPager()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBtn.setOnClickListener{
            findNavController().navigate(R.id.action_hostFragment_to_searchFragment)
        }
    }
    private fun setupLayoutWithViewPager() {
        binding.viewPager.adapter = HomePagerAdapter(this,tabTitles)

        TabLayoutMediator(binding.tabBarLayout , binding.viewPager){ tab , position ->
            tab.text = tabTitles[position]

        }.attach()
    }


}