package com.example.ottzzang.model

data class SignupRes(
    val isSuccess:Boolean,
    val code:Int,
    val message:String,
    val result:PostUserRes
)
