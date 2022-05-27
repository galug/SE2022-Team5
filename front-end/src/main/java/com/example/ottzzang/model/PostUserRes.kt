package com.example.ottzzang.model

import com.google.gson.annotations.SerializedName

data class PostUserRes(
    @SerializedName("userIdx")
    val userIdx:Int,
    @SerializedName("jwt")
    val jwt:String
)
