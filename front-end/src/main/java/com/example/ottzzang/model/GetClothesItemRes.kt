package com.example.ottzzang.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GetClothesItemRes(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("clothesIdx")
    val clothesIdx:Int,
    @SerializedName("file")
    val file: String
)
