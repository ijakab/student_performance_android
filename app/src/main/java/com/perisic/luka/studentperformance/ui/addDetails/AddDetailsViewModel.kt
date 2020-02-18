package com.perisic.luka.studentperformance.ui.addDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.data.remote.model.helpers.UserDetails
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.repo.AuthRepository
import com.perisic.luka.data.repo.UserRepository

class AddDetailsViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val userDetails = MutableLiveData<UserDetails>()
    private val userId = MutableLiveData<Int>()
    val userDetailsResponse = switchMap(userId, userRepository::getUserDetails)
    val addUserDetailsResponse: LiveData<BaseResponse<User>> = switchMap(userDetails) {
        if (userId.value != null) {
            userRepository.editDetails(userId.value!!, it)
        } else {
            MutableLiveData<BaseResponse<User>>()
        }
    }

    fun fetchUserId() = authRepository.fetchToken()

    fun addUserDetails(userDetails: UserDetails) {
        this.userDetails.value = userDetails
    }


    fun getUserDetails(userId: Int) {
        this.userId.value = userId
    }

}