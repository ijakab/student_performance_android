package com.perisic.luka.studentperformance.ui.editUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.repo.UserRepository

class EditUserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val createUserData = MutableLiveData<UserDataRequest>()
    private val editUserData = MutableLiveData<UserDataRequest>()

    val userRole = MutableLiveData<String>()

    fun createUser(createUserDataRequest: UserDataRequest) {
        createUserData.value = createUserDataRequest
    }

    fun createUserResponse(): LiveData<BaseResponse<User>> =
        switchMap(createUserData, userRepository::createUser)

    fun editUser(userDataRequest: UserDataRequest) {
        editUserData.value = userDataRequest
    }

    fun editUserResponse(userId: Int): LiveData<BaseResponse<User>> =
        switchMap(editUserData) {
            userRepository.updateUser(it, userId)
        }

    fun fetchUser(userId: Int) = userRepository.getUser(userId)

}