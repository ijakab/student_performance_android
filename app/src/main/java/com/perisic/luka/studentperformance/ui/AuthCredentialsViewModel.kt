package com.perisic.luka.studentperformance.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.repo.AuthRepository

class AuthCredentialsViewModel(
    authRepository: AuthRepository
) : ViewModel() {

    private val username = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    var existingUsername: String? = null
    var existingEmail: String? = null
    val checkUsernameResponse: LiveData<BaseResponse<Any>> = switchMap(username) {
        if (existingUsername?.equals(it) != true) {
            MutableLiveData<BaseResponse<Any>>(
                BaseResponse(
                    status = BaseResponse.STATUS.SUCCESS,
                    data = "success"
                )
            )
        } else
            authRepository.checkUsername(it)
    }
    val checkEmailResponse = switchMap(email) {
        if (existingEmail != null && !existingEmail.equals(it)) {
            MutableLiveData<BaseResponse<Any>>(
                BaseResponse(
                    status = BaseResponse.STATUS.SUCCESS,
                    data = "success"
                )
            )
        } else
            authRepository.checkEmail(it)
    }

    fun checkUsername(usernameToCheck: String) {
        username.value = usernameToCheck
    }

    fun checkEmail(emailToCheck: String) {
        email.value = emailToCheck
    }

}