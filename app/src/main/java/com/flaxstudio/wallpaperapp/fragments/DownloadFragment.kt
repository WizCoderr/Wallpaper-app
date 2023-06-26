package com.flaxstudio.wallpaperapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding
import com.flaxstudio.wallpaperapp.databinding.FragmentDownloadBinding


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDownloadBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }

    }

}