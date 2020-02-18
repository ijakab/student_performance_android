package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.helpers.FilterRequest
import com.perisic.luka.data.remote.model.helpers.FilterResponse
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.data.remote.model.helpers.UserDetails
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.data.remote.util.BaseResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

interface UserService {

    @POST("user/filter")
    fun filterUsers(@Body filter: FilterRequest): Call<BaseResponse<FilterResponse<User>>>

    @GET("user/get/{userId}")
    fun getUser(@Path("userId") userId: Int): Call<BaseResponse<User>>

    @POST("user/create")
    fun createUser(@Body createUserRequest: UserDataRequest): Call<BaseResponse<User>>

    @PATCH("user/update/{userId}")
    fun updateUser(@Body updateUserRequest: UserDataRequest, @Path("userId") userId: Int): Call<BaseResponse<User>>

    @DELETE("user/delete/{userId}")
    fun deleteUser(@Path("userId") userId: Int): Call<BaseResponse<User>>

    @POST("user/addDetails/{userId}")
    fun editUserDetails(@Path("userId") userId: Int, @Body userDetails: UserDetails): Call<BaseResponse<User>>

    companion object {

        fun create(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }

    }

}