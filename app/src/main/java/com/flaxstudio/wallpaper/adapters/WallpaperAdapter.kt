package com.flaxstudio.wallpaper.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.flaxstudio.wallpaper.utils.DownloadItems
import com.flaxstudio.wallpaperapp.R

class WallpaperAdapter(private val context: Context, private val listItem:List<DownloadItems>):BaseAdapter() {

    inner class ViewHolder(){
         lateinit var img:ImageView
         lateinit var text:TextView
    }
    override fun getCount(): Int =listItem.size

    override fun getItem(position: Int): Any = listItem[position]

    override fun getItemId(position: Int): Long =position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder = ViewHolder()
        val view = LayoutInflater.from(context).inflate(R.layout.items,parent,false)
        view.tag = holder
        holder.img = view.findViewById(R.id.item_img)
        holder.text = view.findViewById(R.id.item_tv)
        holder.img.setImageResource(listItem[position].img)
        holder.text.text = listItem[position].txt
        return view
    }
}