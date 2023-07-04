package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding


class CollectionFragment : Fragment() {
private lateinit var binding: FragmentCollectionBinding
private lateinit var thisContext : Context

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
        val adapter = CollectionRecyclerViewAdapter(thisContext)
        val rv = binding.rvItem
        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
        rv.adapter = adapter
        rv.layoutManager =  gridLayoutManager
    }


}