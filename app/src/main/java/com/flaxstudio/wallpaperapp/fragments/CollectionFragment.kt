package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding
import com.flaxstudio.wallpaperapp.source.fetchCategories
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory


class CollectionFragment : Fragment() {
private lateinit var binding: FragmentCollectionBinding
private lateinit var thisContext : Context
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(inflater ,container , false )
        thisContext = requireContext()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCategories(mainActivityViewModel)
        binding.backBtn.setOnClickListener{findNavController().popBackStack()}

        val adapter = CollectionRecyclerViewAdapter(thisContext)
        val rv = binding.rvItem
        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
        rv.adapter = adapter
        rv.layoutManager =  gridLayoutManager

        adapter.setOnClickListener {
           // findNavController().navigate(R.id.action_collectionFragment_to_downloadFragment)
        }
    }


}