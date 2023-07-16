package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentGalleryBinding

class GalleryFragment() : Fragment() {
    private lateinit var binding : FragmentGalleryBinding
    private lateinit var thisContext : Context

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
            findNavController().navigate(R.id.action_hostFragment_to_downloadFragmen)
        }
    }


}