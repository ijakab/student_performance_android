package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.perisic.luka.data.local.dao.TokenModelDao
import com.perisic.luka.data.local.model.Token
import com.perisic.luka.data.remote.api.AuthService
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.remote.util.makeCall

internal class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenModelDao: TokenModelDao
) : AuthRepository {

    override fun login(loginRequest: LoginRequest): LiveData<BaseResponse<LoginResponse>> {
        return authService.login(loginRequest).makeCall {
            tokenModelDao.insert(
                Token(
                    id = it.data?.user?.id ?: 1,
                    token = it.data?.token,
                    role = it.data?.user?.role
                )
            )
        }
    }

    override fun logout() {
        tokenModelDao.deleteAll()
    }

    override fun tokenCount(): LiveData<Int> {
        return tokenModelDao.tokenCount()
    }

    override fun fetchToken(): LiveData<Token> {
        return tokenModelDao.fetchTokenAsync()
    }

    override fun userRole(): LiveData<String> {
        val response = MediatorLiveData<String>()
        response.addSource(
            tokenModelDao.fetchTokenAsync()
        ) {
            response.value = it?.role
        }
        return response
    }

    override fun checkUsername(username: String): LiveData<BaseResponse<Any>> {
        return authService.checkUsername(username).makeCall()
    }

    override fun checkEmail(email: String): LiveData<BaseResponse<Any>> {
        return authService.checkEmail(email).makeCall()
    }
}