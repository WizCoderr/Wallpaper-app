package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flaxstudio.wallpaperapp.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {
    private lateinit var binding : FragmentGalleryBinding
    private lateinit var thisContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentGalleryBinding.inflate(layoutInflater , container ,false)
        thisContext = requireContext()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = CollectionRecyclerViewAdapter(thisContext)
//        val rv = binding.rvItem
//        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
//        rv.adapter = adapter
//        rv.layoutManager =  gridLayoutManager
//
//        adapter.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_downloadFragment)
//        }
    }


}