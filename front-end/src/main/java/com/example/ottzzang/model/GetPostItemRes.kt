package com.example.ottzzang.model

import com.google.gson.annotations.SerializedName

data class GetPostItemRes(
    @SerializedName("name")
    val name:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("postLikeCount")
    val postLikeCount:Int,
    @SerializedName("likeOrNot")
    val likeOrNot:String,
    @SerializedName("uri")
    val uri:String,
    @SerializedName("file")
    val file:String,
    @SerializedName("postIdx")
    val postIdx:Int
)
