package com.flaxstudio.wallpaperapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaperapp.ProjectApplication
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.adapters.HomeRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentHomeAllBinding
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeFragmentAll : Fragment() {


private lateinit var binding: FragmentHomeAllBinding
private lateinit var contextThis : Context
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAllBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextThis = requireContext()
        lifecycleScope.launch(Dispatchers.Main){
            val datas = mainActivityViewModel.getAllCategories().first()
            val adapters = HomeRecyclerViewAdapter(contextThis,datas)
            binding.recyclerview.apply {
                adapter = adapters
                layoutManager = GridLayoutManager(contextThis, 2)
                adapters.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("categoryId",datas[it]._id)
                    bundle.getString("categoryName",datas[it].title)
                    findNavController().navigate(R.id.action_hostFragment_to_collectionFragment,bundle)
                }
            }
        }
    }


}