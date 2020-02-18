package com.perisic.luka.studentperformance.ui.userList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.UserRepository


class UserListViewModel(
    userRepository: UserRepository
) : ViewModel() {

    private val keyword = MutableLiveData<String>()
    private val userIdToDelete = MutableLiveData<Int>()
    private val userPagedDataList = map(keyword, userRepository::filterUsers)
    val userList = switchMap(userPagedDataList) { it.pagedList }
    val status = switchMap(userPagedDataList) { it.status }
    val deletedUser = switchMap(userIdToDelete, userRepository::deleteUser)

    fun filterUsers(query: String, refresh: Boolean = false) {
        if ((query.isEmpty() && keyword.value?.isEmpty() != true) || (keyword.value != query) || refresh) {
            keyword.value = query
        }
    }

    fun deleteUser(userId: Int) {
        userIdToDelete.value = userId
    }

}