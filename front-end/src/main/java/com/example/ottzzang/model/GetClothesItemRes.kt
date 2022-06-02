package com.example.ottzzang.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GetClothesItemRes(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("season")
    val season:Int,
    @SerializedName("bigCategory")
    val bigCategory:Int,
    @SerializedName("smallCategory")
    val smallCategory:Int,
    @SerializedName("color")
    val color:String,
    @SerializedName("file")
    val file: String
)
