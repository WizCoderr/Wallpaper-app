package com.flaxstudio.wallpaper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaper.ProjectApplication
import com.flaxstudio.wallpaper.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaper.source.database.WallpaperData
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentAllWallpaperBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AllWallpaperFragment : Fragment() {
    lateinit var allWallpaperBinding: FragmentAllWallpaperBinding
    private var data: List<WallpaperData> = ArrayList()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository,
            (requireActivity().application as ProjectApplication).likedWallpaperRepository
        )
    }
    lateinit var thisContext:Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        allWallpaperBinding = FragmentAllWallpaperBinding.inflate(layoutInflater)
        thisContext = requireContext()
        return allWallpaperBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Default){
            mainActivityViewModel.getAllWallpapers().collect {
                data = it
                withContext(Dispatchers.Main){
                    updateRV()
                }
            }

        }
    }

    private fun updateRV() {
        val adapters = CollectionRecyclerViewAdapter(thisContext, data)
        allWallpaperBinding.rvItem.apply {
            adapter = adapters
            layoutManager = GridLayoutManager(thisContext, 3)
            adapters.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("image", data[it].image_url)
                findNavController().navigate(
                    R.id.action_hostFragment_to_downloadFragment,
                    bundle
                )
            }
        }
    }
}