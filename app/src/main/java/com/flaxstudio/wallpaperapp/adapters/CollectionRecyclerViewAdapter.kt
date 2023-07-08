package com.flaxstudio.wallpaperapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R

class CollectionRecyclerViewAdapter(private val context : Context) : RecyclerView.Adapter<CollectionRecyclerViewAdapter.ViewHolder>() {
    private var itemClickListener:((position:Int )->Unit)? = null



    fun setOnClickListener(callback:(position:Int)->Unit){
        itemClickListener = callback
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.img)!!

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_item , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 300
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(R.drawable.lovingone).into(holder.image)
    }
}
