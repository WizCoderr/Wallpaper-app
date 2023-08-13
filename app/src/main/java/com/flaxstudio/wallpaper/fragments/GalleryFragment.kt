package com.flaxstudio.wallpaper.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flaxstudio.wallpaper.ProjectApplication
import com.flaxstudio.wallpaper.adapters.GalleryAdapter
import com.flaxstudio.wallpaper.utils.FragmentType
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.databinding.FragmentGalleryBinding
import kotlinx.coroutines.launch

class GalleryFragment(fragmentType: FragmentType) : Fragment() {


    private lateinit var binding : FragmentGalleryBinding
    private lateinit var thisContext : Context
    private val fragmentType: FragmentType
    private lateinit var adapter: GalleryAdapter


    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory(
            (requireActivity().application as ProjectApplication).wallpaperRepository,
            (requireActivity().application as ProjectApplication).categoryRepository,
        )
    }



    init {
        this.fragmentType = fragmentType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentGalleryBinding.inflate(layoutInflater)
        thisContext = requireContext()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(thisContext , "Gallery Fragment" , Toast.LENGTH_SHORT).show()

        setUpView()
        setUpData()




//        val adapter = CollectionRecyclerViewAdapter(thisContext, emptyList())
//        val rv = binding.rvItem
//        val  gridLayoutManager = GridLayoutManager(thisContext , 3)
//        rv.adapter = adapter
//        rv.layoutManager =  gridLayoutManager
//
//        adapter.setOnClickListener {
//            findNavController().navigate(R.id.action_hostFragment_to_downloadFragment)
//        }


    }


    private fun setUpView() {


        adapter = GalleryAdapter(thisContext , fragmentType)
        val  gridLayoutManager = GridLayoutManager(thisContext , 3)

        binding.rvItem.adapter = adapter
        binding.rvItem.layoutManager = gridLayoutManager

        Log.i("GalleryFragment" , "setup View is called")


    }

    private fun setUpData()  = lifecycleScope.launch{

        Log.i("GalleryFragment" , "setup data start is called")


        when(fragmentType){
            FragmentType.Nature -> {

                mainActivityViewModel.getWallpapersCategorised("1319040").collect(){
                    adapter.addData(it)
                    Log.i("GalleryFragment" , "nature data : $it")
                }


            }
            FragmentType.Space ->{
                mainActivityViewModel.getWallpapersCategorised("4332580").collect(){
                    adapter.addData(it)
                    Log.i("GalleryFragment" , "nature data : $it")
                }

            }
            FragmentType.Animals ->{
                mainActivityViewModel.getWallpapersCategorised("8613876").collect(){
                    adapter.addData(it)
                    Log.i("GalleryFragment" , "nature data : $it")
                }

            }

        }




    }




}