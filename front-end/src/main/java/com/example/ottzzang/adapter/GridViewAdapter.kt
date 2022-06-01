package com.example.ottzzang.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast

class GridViewAdapter: BaseAdapter() {
    var clothesList: ArrayList<ClothesItem> = ArrayList()

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
        val imageView:ImageView

        if(convertView==null){
            imageView = ImageView(context)
            imageView.run {
                layoutParams = ViewGroup.LayoutParams(400,400)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setPadding(10,10,10,10)
            }
        }else{
            imageView = convertView as ImageView
        }
        imageView.setImageBitmap(clothesItem.img)
        return imageView
    }
    fun addItem(item:ClothesItem){
        clothesList.add(item)
    }
}