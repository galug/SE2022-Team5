package com.example.ottzzang.adapter

import android.graphics.Bitmap

data class ClothesItem(
    var img:Bitmap,
//    var postIdx:Int
    var season:Int,
    var big: Int,
    var small: Int,
    var color:String,
)
