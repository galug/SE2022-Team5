package com.example.ottzzang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.ottzzang.R

class GridViewAdapter: BaseAdapter() {
    var clothesList: ArrayList<ClothesItem> = ArrayList()

    fun addItem(item:ClothesItem){
        clothesList.add(item)
    }
    fun clearAdapter(){
        clothesList.clear()
    }

    override fun getCount(): Int {
        return clothesList.size
    }

    override fun getItem(position: Int): Any {
        return clothesList.get(position)
    }


    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val clothesItem = clothesList.get(position)
        val inflater:LayoutInflater

        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val clothesView : View = inflater.inflate(R.layout.clothes_item,parent,false)

        val clothesImage = clothesView.findViewById<ImageView>(R.id.clothesImage)
        clothesImage.setImageBitmap(clothesItem.img)

        return clothesView
    }
}