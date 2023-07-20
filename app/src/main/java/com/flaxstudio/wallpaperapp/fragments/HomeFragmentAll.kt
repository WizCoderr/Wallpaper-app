package com.flaxstudio.wallpaperapp.fragments

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
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.HomeRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentHomeAllBinding
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragmentAll : Fragment() {


    private lateinit var binding: FragmentHomeAllBinding
    private lateinit var contextThis : Context
    private var allData:List<WallpaperCategoryData> = ArrayList()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAllBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextThis = requireContext()
        lifecycleScope.launch(Dispatchers.Main){
            mainActivityViewModel.getAllCategories().collect{
                allData = it
                updateRecylerView()
            }

        }
    }

    private fun updateRecylerView() {
        val adapters = HomeRecyclerViewAdapter(contextThis,allData)
        binding.recyclerview.apply {
            adapter = adapters
            layoutManager = GridLayoutManager(contextThis, 2)
            adapters.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("categoryId",allData[it]._id)
                    putString("categoryName",allData[it].title)
                }
                findNavController().navigate(R.id.action_hostFragment_to_collectionFragment,bundle)
            }
        }
    }


}