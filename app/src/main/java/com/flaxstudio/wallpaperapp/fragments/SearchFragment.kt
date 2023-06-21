package com.flaxstudio.wallpaperapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.FragmentAccountBinding
import com.flaxstudio.wallpaperapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater , container ,false)
        return binding.root
    }

}