package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse
import com.perisic.luka.data.remote.util.BaseResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<BaseResponse<LoginResponse>>

    @POST("auth/checkUsername")
    fun checkUsername(@Query("username") username: String): Call<BaseResponse<Any>>

    @POST("auth/checkEmail")
    fun checkEmail(@Query("email") email: String): Call<BaseResponse<Any>>

    companion object {

        fun create(retrofit: Retrofit): AuthService =
            retrofit.create(AuthService::class.java)

    }

}