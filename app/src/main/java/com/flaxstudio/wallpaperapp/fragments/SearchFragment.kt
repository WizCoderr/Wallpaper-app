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
import androidx.recyclerview.widget.RecyclerView
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = requireContext()
        binding.search.setOnClickListener { getSearchData(binding.searchEditText.text.toString(),currentPage)}
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
    }
    private var wallpaperDataList = mutableListOf<WallpaperData>()
    private var currentPage = 1

    private fun getSearchData(query: String,size:Int) {
        RetrofitClient.wallpaperApi.searchWallpapers(query, size).enqueue(object :
            Callback<List<WallpaperData>> {
            override fun onResponse(
                call: Call<List<WallpaperData>>,
                response: Response<List<WallpaperData>>
            ) {
                if (response.isSuccessful) {
                    setupScrollListener()
                    val result = response.body()
                    if (result.isNullOrEmpty()) {
                        // No wallpapers left to load
                        return
                    }

                    wallpaperDataList.addAll(result)
                    updateAdapter()
                    if (wallpaperDataList.size == 30){
                        getSearchData(query,currentPage++)
                    }
                }
            }

            override fun onFailure(call: Call<List<WallpaperData>>, t: Throwable) {
                Log.e("TAG", "onResponse: result ${t.message}")
            }
        })
    }

    private fun updateAdapter() {
        binding.searchList.apply {
            adapter = SearchListAdapter(thisContext, wallpaperDataList)
            // Update the adapter with the new data
            layoutManager = GridLayoutManager(thisContext, 3)
        }
    }

    private fun setupScrollListener() {
        binding.searchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    val query = binding.searchEditText.text.toString()
                    getSearchData(query,currentPage)
                }
            }
        })
    }
}