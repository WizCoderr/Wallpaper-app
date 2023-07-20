package com.flaxstudio.wallpaper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flaxstudio.wallpaperapp.databinding.FragmentFavoratesBinding

class FavoritesFragment : Fragment() {
    lateinit var favoritesFragment: FragmentFavoratesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favoritesFragment = FragmentFavoratesBinding.inflate(layoutInflater)
        return favoritesFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}