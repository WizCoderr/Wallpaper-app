package com.flaxstudio.wallpaper.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.adapters.FavouritesAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentFavoratesBinding
import com.flaxstudio.wallpaperapp.source.database.LikedWallpaper
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private var TAG = "FavouritesFragment"
class FavoritesFragment : Fragment() {
    lateinit var binding: FragmentFavoratesBinding
    private lateinit var thisContext : Context
    private lateinit var adapters : FavouritesAdapter

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
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
        // Inflate the layout for this fragment
        binding = FragmentFavoratesBinding.inflate(layoutInflater)
        thisContext = requireContext()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener{findNavController().popBackStack()}

        lifecycleScope.launch {
            mainActivityViewModel.getAllLikedWallpaper().collect{
                 adapters = FavouritesAdapter(thisContext,it)
                Log.i(TAG , "data received from room is : $it")
                val layoutManager = GridLayoutManager(thisContext, 3)
                binding.rvItem.adapter =adapters
                binding.rvItem.layoutManager = layoutManager
            }
        }

    }
}