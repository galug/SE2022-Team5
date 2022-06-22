package com.example.ottzzang.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @POST("posts/like")
    fun requestLikePosts(
        @Body postPostLikeReq:PostPostLikReq
    ):retrofit2.Call<PostPostLikeRes>

    @PATCH("posts/{postIdx}/status")
    fun requestDeletePosts(
        @Path("postIdx") postIdx:Int
    ):retrofit2.Call<DeletePostRes>
}