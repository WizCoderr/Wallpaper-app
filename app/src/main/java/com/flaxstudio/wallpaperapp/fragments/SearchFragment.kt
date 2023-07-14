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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = requireContext()
        binding.search.setOnClickListener { getSearchData(binding.searchEditText.text.toString()) }
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
    }
    private fun  getSearchData(query:String){
        val pgNumb = 1
        RetrofitClient.wallpaperApi.searchWallpapers(query,pgNumb).enqueue(object :
            Callback<List<WallpaperData>>{
            override fun onResponse(
                call: Call<List<WallpaperData>>,
                response: Response<List<WallpaperData>>
            ) {
                if (response.isSuccessful){
                    val result = response.body()
                    Log.d("TAG", "onResponse: $result")
                    binding.searchList.apply {
                        adapter = result?.let { SearchListAdapter(thisContext, it) }
                        layoutManager = GridLayoutManager(thisContext,3)
                    }

                }
            }

            override fun onFailure(call: Call<List<WallpaperData>>, t: Throwable) {
                Log.e("TAG", "onResponse: result ${t.message}")

            }

        })
    }
}