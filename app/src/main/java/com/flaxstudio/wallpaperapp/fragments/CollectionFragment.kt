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
import com.flaxstudio.wallpaperapp.adapters.CollectionRecyclerViewAdapter
import com.flaxstudio.wallpaperapp.databinding.FragmentCollectionBinding
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaperapp.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class CollectionFragment : Fragment() {
private lateinit var binding: FragmentCollectionBinding
private lateinit var thisContext : Context
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository
        )
    }
    lateinit var receivedData:String


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
        receivedData = requireArguments().getString("categoryId").toString()
        binding.tvCollectionName.text = requireArguments().getString("categoryTittle").toString()
        lifecycleScope.launch(Dispatchers.Main){
            val datas = mainActivityViewModel.getWallpapersCategorised(receivedData).first()
            val adapters = CollectionRecyclerViewAdapter(thisContext,datas)
            binding.rvItem.apply {
                adapter = adapters
                layoutManager = GridLayoutManager(thisContext, 2)
                adapters.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("categoryImg",datas[it].image_url)
                    bundle.putInt("collect",1)
                    findNavController().navigate(R.id.action_hostFragment_to_downloadFragmen,bundle)
                }
            }
        }

    }


}