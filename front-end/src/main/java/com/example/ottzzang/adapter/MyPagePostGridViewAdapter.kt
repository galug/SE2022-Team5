package com.example.ottzzang.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ottzzang.R
import com.example.ottzzang.ui.PostInfoActivity

class MyPagePostGridViewAdapter: BaseAdapter() {
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

        val inflater: LayoutInflater

        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val postView : View = inflater.inflate(R.layout.post_title_item,parent,false)

        val subject = postView.findViewById<TextView>(R.id.subject)
        subject.setText(postItem.title)


        return postView
    }


    fun addItem(item:PostItem){
        postList.add(item)
    }

}