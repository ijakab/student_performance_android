package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.model.Token
import com.perisic.luka.data.model.SpinnerChoice
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.data.remote.model.helpers.UserDetails
import com.perisic.luka.data.remote.model.helpers.UserDetailsParsed
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.data.remote.model.response.LoginResponse
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.source.PagedData

interface AuthRepository {

    fun login(loginRequest: LoginRequest): LiveData<BaseResponse<LoginResponse>>

    fun logout()

    fun tokenCount(): LiveData<Int>

    fun fetchToken(): LiveData<Token>

    fun userRole(): LiveData<String>

    fun checkUsername(username: String): LiveData<BaseResponse<Any>>

    fun checkEmail(email: String): LiveData<BaseResponse<Any>>

}

interface UserRepository {

    fun filterUsers(keyword: String): PagedData<User>

    fun getUser(userId: Int): LiveData<BaseResponse<User>>

    fun getUserDetails(userId: Int): LiveData<BaseResponse<UserDetailsParsed>>

    fun createUser(userRequest: UserDataRequest): LiveData<BaseResponse<User>>

    fun updateUser(
        updateUserDataRequest: UserDataRequest,
        userId: Int
    ): LiveData<BaseResponse<User>>

    fun deleteUser(userId: Int): LiveData<BaseResponse<User>>

    fun editDetails(userId: Int, userDetails: UserDetails): LiveData<BaseResponse<User>>

    fun spinnerDataList(): LiveData<List<SpinnerChoice>>

}