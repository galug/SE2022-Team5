package com.example.ottzzang.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ottzzang.R

class PostGridViewAapter:BaseAdapter() {
    var postList: ArrayList<PostItem> = ArrayList()
    override fun getCount(): Int {
        return postList.size
    }

    override fun getItem(position: Int): Any {
        return postList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val postItem = postList.get(position)

        val inflater:LayoutInflater

        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val postView : View = inflater.inflate(R.layout.post_item,parent,false)

        val postImage = postView.findViewById<ImageView>(R.id.postImage)
        val likeOuterCount = postView.findViewById<TextView>(R.id.likeOuterCount)
        postImage.setImageBitmap(postItem.img)
        likeOuterCount.setText(postItem.postLikeCount.toString())

        return postView
    }

    fun addItem(item:PostItem){
        postList.add(item)
    }
}