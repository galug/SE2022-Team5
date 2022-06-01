package com.example.ottzzang.model

data class GetClothesListReq(
    var userIdx:Int,
    var bigCategory: Int,
    var smallCategory: Int,
    var season: Int
)
