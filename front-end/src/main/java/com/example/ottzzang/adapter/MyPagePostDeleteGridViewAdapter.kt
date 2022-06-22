package com.example.ottzzang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ottzzang.R

class MyPagePostDeleteGridViewAdapter: BaseAdapter() {
    var postList: ArrayList<PostDeleteItem> = ArrayList()
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

        val inflater: LayoutInflater

        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val postView : View = inflater.inflate(R.layout.post_delelte_item,parent,false)


        return postView
    }


    fun addItem(item:PostDeleteItem){
        postList.add(item)
    }
}