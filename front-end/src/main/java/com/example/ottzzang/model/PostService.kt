package com.example.ottzzang.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostService {
    @Multipart
    @POST("posts")
    fun requestAddPosts(
        @Part imgFile: MultipartBody.Part,
        @Part("userIdx") userIdx: RequestBody,
        @Part("title") title: RequestBody
    ): retrofit2.Call<PostPostRes>

    @POST("posts/lookup")
    fun requestGetPosts(
        @Body getCPostsReq:GetPostListReq
    ): retrofit2.Call<GetPostListRes>
}