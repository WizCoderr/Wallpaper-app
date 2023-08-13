package com.flaxstudio.wallpaper.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaper.ProjectApplication
import com.flaxstudio.wallpaper.adapters.SearchListAdapter
import com.flaxstudio.wallpaper.source.api.RetrofitClient
import com.flaxstudio.wallpaper.source.database.WallpaperData
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import retrofit2.Response


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var thisContext:Context
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository,
        )
    }
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
    private val wallpaperDataList = mutableListOf<WallpaperData>()

    private fun getSearchData(query: String) {
        val totalPages = 80

        val deferredResults = mutableListOf<Deferred<Response<List<WallpaperData>>>>()

        val scope = CoroutineScope(Dispatchers.IO)

        try {
            for (page in 1..totalPages) {
                val deferred = scope.async {
                    RetrofitClient.wallpaperApi.searchWallpapers(query, page).execute()
                }
                deferredResults.add(deferred)
            }

            runBlocking {
                val responses = deferredResults.awaitAll()

                responses.forEachIndexed { index, response ->
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (!result.isNullOrEmpty()) {
                            wallpaperDataList.addAll(result)
                        }

                        if (index == responses.lastIndex) {
                            updateAdapter()
                        }
                    } else {
                        // Handle API call failure
                        Log.e("TAG", "onResponse: result ${response.code()}")
                    }
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("TAG", "Exception: ${e.message}")
        }
    }

    private fun updateAdapter() {
        binding.searchList.apply {
            adapter = SearchListAdapter(thisContext, wallpaperDataList)
            layoutManager = GridLayoutManager(thisContext, 3)
        }
    }


}