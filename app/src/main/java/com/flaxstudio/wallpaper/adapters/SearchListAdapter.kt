package com.flaxstudio.wallpaper.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaper.source.database.WallpaperData
import com.flaxstudio.wallpaperapp.R

class SearchListAdapter(private val context: Context,private val searchData:List<WallpaperData>):RecyclerView.Adapter<SearchListAdapter.SearchView>() {

    inner class SearchView(itemView: View) : RecyclerView.ViewHolder(itemView) {
         fun bind(data: WallpaperData) {
             Glide.with(context)
                 .load(data.image_url+"?fm=jpg&w=200&fit=max")
                 .into(itemView.findViewById(R.id.img))
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bundle = Bundle()
                    bundle.putString("image",data.image_url)
                    bundle.putString("blurHash" , data.blur_hash)
                    bundle.putInt("collect",0)
                    Navigation.findNavController(itemView).navigate(R.id.action_searchFragment_to_downloadFragment,bundle)
                }
            }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchView = SearchView(LayoutInflater.from(context).inflate(R.layout.collection_item, parent, false))

        override fun getItemCount(): Int = searchData.size

        override fun onBindViewHolder(holder: SearchView, position: Int) =
            holder.bind(searchData[position])
}