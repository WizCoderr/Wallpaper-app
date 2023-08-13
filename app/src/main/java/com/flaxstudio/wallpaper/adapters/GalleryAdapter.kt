package com.flaxstudio.wallpaper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaper.source.database.WallpaperData
import com.flaxstudio.wallpaper.utils.FragmentType
import com.flaxstudio.wallpaperapp.R

class GalleryAdapter (private val context: Context, private val adapterType: FragmentType) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {


    private var wallpaperData = listOf<WallpaperData>()




    fun addData (data : List<WallpaperData>){
        wallpaperData = data
    }


    inner  class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.findViewById(R.id.img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_item , parent , false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val  data = wallpaperData[position]

        Glide.with(context).load(R.drawable.lovingone).into(holder.imageView)
    }
}