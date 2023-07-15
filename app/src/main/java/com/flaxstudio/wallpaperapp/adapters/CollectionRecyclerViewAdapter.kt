package com.flaxstudio.wallpaperapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.source.database.WallpaperData

class CollectionRecyclerViewAdapter(private val context : Context,private val data:List<WallpaperData>) : RecyclerView.Adapter<CollectionRecyclerViewAdapter.ViewHolder>() {
    private var itemClickListener:((position:Int )->Unit)? = null



    fun setOnClickListener(callback:(position:Int)->Unit){
        itemClickListener = callback
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: WallpaperData){
            Glide.with(context).load(data.image_url).into( itemView.findViewById(R.id.img))
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.collection_item , parent , false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =holder.bind(data[position])
}
