package com.example.ottzzang.model

data class GetPostListRes(
    val isSuccess:Boolean,
    val code:Int,
    val message:String,
    val result: List<GetPostItemRes>
)
