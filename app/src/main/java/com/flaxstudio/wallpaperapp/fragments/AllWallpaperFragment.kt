package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentAllWallpaperBinding
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AllWallpaperFragment : Fragment() {
    lateinit var allWallpaperBinding: FragmentAllWallpaperBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository
        )
    }
    lateinit var thisContext:Context
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View{
        allWallpaperBinding = FragmentAllWallpaperBinding.inflate(layoutInflater)
        thisContext = requireContext()
        return allWallpaperBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val data = mainActivityViewModel.getAllWallpapers().first()
            allWallpaperBinding.rvItem.apply {
                adapter = CollectionRecyclerViewAdapter(thisContext,data)
                layoutManager = GridLayoutManager(thisContext,3)
            }
        }
    }
}