package com.example.ottzzang.model

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    fun requestLogin(
        @Body login: LoginReq
    ) : retrofit2.Call<LoginRes>

    @POST("users")
    fun requestSignup(
        @Body signup: SignupReq
    ): retrofit2.Call<SignupRes>

}