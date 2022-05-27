package com.example.ottzzang.model

import com.google.gson.annotations.SerializedName

data class LoginRes(
    val isSuccess:Boolean,
    val code:Int,
    val message:String,
    val result:PostUserRes
)

