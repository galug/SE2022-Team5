package com.example.ottzzang.adapter

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class PostItem(
    val name:String,
    val title:String,
    val postLikeCount:Int,
    val likeOrNot:String,
    val img:Bitmap,
    val postIdx:Int
)
