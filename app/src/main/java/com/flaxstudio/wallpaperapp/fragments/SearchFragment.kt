package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.adapters.SearchListAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentSearchBinding
import com.flaxstudio.wallpaperapp.source.api.RetrofitClient
import com.flaxstudio.wallpaperapp.source.database.WallpaperData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var thisContext:Context
    private val wallpaperDataList = mutableListOf<WallpaperData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = requireContext()
        binding.search.setOnClickListener { getSearchData(binding.searchEditText.text.toString())}
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
    }
    private fun getSearchData(query: String) {
        for (page in 1..80) {
            RetrofitClient.wallpaperApi.searchWallpapers(query, page).enqueue(object : Callback<List<WallpaperData>> {
                override fun onResponse(call: Call<List<WallpaperData>>, response: Response<List<WallpaperData>>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result.isNullOrEmpty()) {
                            // No wallpapers found for this page
                            return
                        }

                        wallpaperDataList.addAll(result)

                        if (page == 80) {
                            // All pages have been loaded
                            // Perform any final actions or UI updates
                            return
                        }
                    }
                }

                override fun onFailure(call: Call<List<WallpaperData>>, t: Throwable) {
                    // Handle API call failure
                }
            })
        }
    }

    private fun updateAdapter() {
        binding.searchList.apply{
            adapter = SearchListAdapter(thisContext,wallpaperDataList)
            layoutManager = GridLayoutManager(thisContext,3)
        }
    }
}