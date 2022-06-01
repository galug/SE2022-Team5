package com.example.ottzzang.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ClothesService {
    @Multipart
    @POST("clothes")
    fun requestAddClothes(
        @Part imgFile: MultipartBody.Part,
//        @Part userIdx: MultipartBody.Part,
//        @Part season: MultipartBody.Part,
//        @Part bigCategory: MultipartBody.Part,
//        @Part smallCategory: MultipartBody.Part,
//        @Part color: MultipartBody.Part
        @Part("userIdx") userIdx: RequestBody,
        @Part("season") season: RequestBody,
        @Part("bigCategory") bigCategory: RequestBody,
        @Part("smallCategory") smallCategory: RequestBody,
        @Part("color") color: RequestBody
    ): retrofit2.Call<PostClothesRes>

    @POST("clothes/lookup")
    fun requestGetClothes(
        @Body getClothesListReq:GetClothesListReq
    ): retrofit2.Call<GetClothesListRes>
}