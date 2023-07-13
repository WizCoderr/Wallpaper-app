package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.adapters.HomeRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentHomeAllBinding
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory


class HomeFragmentAll : Fragment() {


private lateinit var binding: FragmentHomeAllBinding
private lateinit var contextThis : Context
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
        // Inflate the layout for this fragment
        binding = FragmentHomeAllBinding.inflate(layoutInflater, container , false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextThis = requireContext()

        val adapter = HomeRecyclerViewAdapter(contextThis)
        val gridLayoutManager = GridLayoutManager(contextThis , 2)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = gridLayoutManager





    }


}