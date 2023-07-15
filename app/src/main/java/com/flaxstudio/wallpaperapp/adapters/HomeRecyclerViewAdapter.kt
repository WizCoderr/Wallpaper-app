package com.flaxstudio.wallpaperapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.source.database.WallpaperCategoryData

class HomeRecyclerViewAdapter(private val context: Context,collectionList: List<WallpaperCategoryData>) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>()  {

    private var itemClickListener:((position:Int )->Unit)? = null


    fun setOnClickListener(callback:(position:Int)->Unit){
        itemClickListener = callback
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val coverPhoto: ImageView = itemView.findViewById(R.id.coverPhoto)
        val collectionName: TextView = itemView.findViewById(R.id.collection_name)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(position)
                }
            }

        }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_collection_item , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 18
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(R.drawable.lovingone).into(holder.coverPhoto)
        holder.collectionName.text = "Animals"

    }


}