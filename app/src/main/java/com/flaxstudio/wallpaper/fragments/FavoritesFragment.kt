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
import com.flaxstudio.wallpaper.source.database.LikedWallpaper
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.FavouritesAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentFavoratesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var TAG = "FavouritesFragment"
class FavoritesFragment : Fragment() {
    lateinit var binding: FragmentFavoratesBinding
    private lateinit var thisContext : Context
    private lateinit var adapters : FavouritesAdapter
    private var  likedWallpaper:List<LikedWallpaper> = ArrayList()

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

        lifecycleScope.launch(Dispatchers.Default) {
            mainActivityViewModel.getAllLikedWallpaper().collect{
                likedWallpaper = it
                withContext(Dispatchers.Main){
                    updateRv()
                }
            }
        }
    }
    private fun updateRv(){

        val adapters = FavouritesAdapter(thisContext,likedWallpaper)
        binding.rvItem.apply {
            adapter = adapters
            layoutManager = GridLayoutManager(thisContext, 3)
            adapters.setOnClickListener {pog->
                val bundle = Bundle()
                bundle.putString("data", likedWallpaper[pog].image_url)
                bundle.putInt("collect",2)
                findNavController().navigate(
                    R.id.action_hostFragment_to_downloadFragment,
                    bundle
                )
            }
        }
    }
}