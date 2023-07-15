package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
        binding.search.setOnClickListener { loadNextWallpapers(binding.searchEditText.text.toString(),currentPage)}
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
    }
    private var currentPage = 1
    private var requestCount = 0
    private val wallpaperDataList = mutableListOf<WallpaperData>()

    private fun getSearchData(query: String, size: Int) {
        RetrofitClient.wallpaperApi.searchWallpapers(query, size).enqueue(object :
            Callback<List<WallpaperData>> {
            override fun onResponse(
                call: Call<List<WallpaperData>>,
                response: Response<List<WallpaperData>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result.isNullOrEmpty()) {
                        // No wallpapers left to load
                        return
                    }

                    wallpaperDataList.addAll(result)
                    updateAdapter()

                    requestCount++ // Increment the request count

                    if (requestCount == 30) {
                        requestCount = 0 // Reset the request count
                        currentPage++ // Increment the current page
                    }
                }
            }

            override fun onFailure(call: Call<List<WallpaperData>>, t: Throwable) {
                Log.e("TAG", "onResponse: result ${t.message}")
            }
        })
    }

    private fun updateAdapter() {
        binding.searchList.apply{
            adapter = SearchListAdapter(thisContext,wallpaperDataList)
            layoutManager = GridLayoutManager(thisContext,3)
        }
    }

    // Call this function to load the next 30 wallpapers
    private fun loadNextWallpapers(query: String,size: Int) {
        if (requestCount == 0 && currentPage >= 1) {
            getSearchData(query, size)
        }
    }


}