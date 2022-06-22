package com.example.ottzzang.model

import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("auth/login")
    fun requestLogin(
        @Body login: LoginReq
    ) : retrofit2.Call<LoginRes>

    @POST("users")
    fun requestSignup(
        @Body signup: SignupReq
    ): retrofit2.Call<SignupRes>

    @PATCH("users/{userIdx}/status")
    fun requestDeleteUsers(
        @Path("userIdx") userIdx:Int
    ):retrofit2.Call<DeleteUserRes>


}