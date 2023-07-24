package com.flaxstudio.wallpaper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaper.ProjectApplication
import com.flaxstudio.wallpaper.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaper.source.database.WallpaperData
import com.flaxstudio.wallpaper.utils.Gallery
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentGalleryBinding

class GalleryFragment(category:Gallery) : Fragment() {
    private lateinit var binding : FragmentGalleryBinding
    private lateinit var thisContext : Context
    private var list:List<WallpaperData> = ArrayList()
    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository,
            (requireActivity().application as ProjectApplication).likedWallpaperRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentGalleryBinding.inflate(layoutInflater)
        thisContext = requireContext()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CollectionRecyclerViewAdapter(thisContext, emptyList())
        val rv = binding.rvItem
        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
        rv.adapter = adapter
        rv.layoutManager =  gridLayoutManager

        adapter.setOnClickListener {
            findNavController().navigate(R.id.action_hostFragment_to_downloadFragment)
        }
    }

}