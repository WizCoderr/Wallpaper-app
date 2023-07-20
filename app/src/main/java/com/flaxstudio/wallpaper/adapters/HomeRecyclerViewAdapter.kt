package com.flaxstudio.wallpaper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaper.source.database.WallpaperCategoryData
import com.flaxstudio.wallpaperapp.R

class HomeRecyclerViewAdapter(private val context: Context,private val data: List<WallpaperCategoryData>) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>()  {

    private var itemClickListener:((position:Int )->Unit)? = null


    fun setOnClickListener(callback:(position:Int)->Unit){
        itemClickListener = callback
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: WallpaperCategoryData) {
            Glide.with(context).load(data.cover_photo).into(itemView.findViewById(R.id.coverPhoto))
            itemView.findViewById<TextView>(R.id.collection_name).text = data.title
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_collection_item , parent , false))
    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


}