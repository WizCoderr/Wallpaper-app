package com.flaxstudio.wallpaperapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.source.database.WallpaperData

class SearchListAdapter(private val context: Context,private val searchData:List<WallpaperData>):RecyclerView.Adapter<SearchListAdapter.SearchView>() {

    inner class SearchView(itemView: View) : RecyclerView.ViewHolder(itemView) {
         fun bind(data: WallpaperData) {
             Glide.with(context)
                 .load(data.image_url)
                 .into(itemView.findViewById(R.id.img))
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bundle = Bundle()
                    bundle.putString("image",data.image_url)
                    Navigation.findNavController(itemView).navigate(R.id.action_searchFragment_to_downloadFragment,bundle)
                }
            }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchView = SearchView(
            LayoutInflater.from(context).inflate(R.layout.collection_item, parent, false)
        )

        override fun getItemCount(): Int = searchData.size

        override fun onBindViewHolder(holder: SearchView, position: Int) =
            holder.bind(searchData[position])
}