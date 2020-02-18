package com.perisic.luka.studentperformance.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.repo.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val loginRequest = MutableLiveData<LoginRequest>()

    fun login(username: String, password: String) {
        loginRequest.value = LoginRequest(username, password)
    }

    fun loginResponse() = switchMap(loginRequest, authRepository::login)

    fun logout() = authRepository.logout()

}