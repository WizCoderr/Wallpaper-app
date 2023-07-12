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
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding
import com.flaxstudio.wallpaperapp.source.api.RetrofitClient.wallpaperApi
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CollectionFragment : Fragment() {
private lateinit var binding: FragmentCollectionBinding
private lateinit var thisContext : Context
private lateinit var wallpaperCategoryData: List<WallpaperCategoryData>

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

        binding.backBtn.setOnClickListener{findNavController().popBackStack()}
        fetchCategories()
        val adapter = CollectionRecyclerViewAdapter(thisContext)
        val rv = binding.rvItem
        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
        rv.adapter = adapter
        rv.layoutManager =  gridLayoutManager

        adapter.setOnClickListener {
            findNavController().navigate(R.id.action_collectionFragment_to_downloadFragment)
        }
    }

    private fun fetchCategories() {
        val call: Call<List<WallpaperCategoryData>> = wallpaperApi.getWallpaperCategoryData()
        call.enqueue(object : Callback<List<WallpaperCategoryData>> {
            override fun onResponse(call: Call<List<WallpaperCategoryData>>, response: Response<List<WallpaperCategoryData>>) {
                if (response.isSuccessful) {
                    // Process the successful response here
                    val categories: List<WallpaperCategoryData>? = response.body()
                    Log.d("TAG", "onResponse: $categories[0]")
                    // ...

                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<List<WallpaperCategoryData>>, t: Throwable) {
                // Handle network failure
            }
        })
    }

}