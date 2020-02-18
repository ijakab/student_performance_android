package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.perisic.luka.data.model.Choice
import com.perisic.luka.data.model.SpinnerChoice
import com.perisic.luka.data.remote.api.UserService
import com.perisic.luka.data.remote.model.helpers.FilterRequest
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.data.remote.model.helpers.UserDetails
import com.perisic.luka.data.remote.model.helpers.UserDetailsParsed
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.data.remote.util.makeCall
import com.perisic.luka.data.source.BasePagedKeyDataSourceFactory
import com.perisic.luka.data.source.PagedData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {

    private val networkExecutor: Executor = Executors.newFixedThreadPool(5)
    private val status = MutableLiveData<BaseResponse.STATUS>()
    private val spinnerChoiceList = MutableLiveData<List<SpinnerChoice>>(
        createSpinnerDataList(null)
    )

    override fun filterUsers(keyword: String): PagedData<User> {

        val sourceFactory = BasePagedKeyDataSourceFactory(
            apiSource = userService::filterUsers,
            filterRequest = FilterRequest(
                keywords = arrayListOf(keyword)
            ),
            status = status
        )

        return PagedData(
            pagedList = sourceFactory.toLiveData(
                pageSize = 50,
                fetchExecutor = networkExecutor
            ),
            status = status
        )
    }

    override fun getUser(userId: Int): LiveData<BaseResponse<User>> {
        return userService.getUser(userId).makeCall {
            it.data?.details?.let { details ->
                createSpinnerDataList(details)
            }
        }
    }

    override fun getUserDetails(userId: Int): LiveData<BaseResponse<UserDetailsParsed>> {
        val response = MediatorLiveData<BaseResponse<UserDetailsParsed>>()
        response.addSource(userService.getUser(userId).makeCall()) {
            response.value = BaseResponse(
                data = it.data?.details?.let { userDetails -> UserDetailsParsed(userDetails) },
                message = it.message,
                status = it.status
            )
        }
        return response
    }

    override fun createUser(userRequest: UserDataRequest): LiveData<BaseResponse<User>> {
        return userService.createUser(userRequest).makeCall()
    }

    override fun updateUser(
        updateUserDataRequest: UserDataRequest,
        userId: Int
    ): LiveData<BaseResponse<User>> {
        return userService.updateUser(updateUserDataRequest, userId).makeCall()
    }

    override fun deleteUser(userId: Int): LiveData<BaseResponse<User>> {
        return userService.deleteUser(userId).makeCall()
    }

    override fun editDetails(userId: Int, userDetails: UserDetails): LiveData<BaseResponse<User>> {
        return userService.editUserDetails(userId, userDetails).makeCall()
    }

    override fun spinnerDataList(): LiveData<List<SpinnerChoice>> {
        return spinnerChoiceList
    }

    private fun createSpinnerDataList(userDetails: UserDetails?): ArrayList<SpinnerChoice> {
        val list = ArrayList<SpinnerChoice>()
            .apply {
                addAll(
                    arrayListOf(
                        SpinnerChoice(
                            type = "sex",
                            hint = "Sex",
                            choices = arrayListOf("Sex", "Male", "Female")
                        ),
                        SpinnerChoice(
                            type = "address",
                            hint = "Address",
                            choices = arrayListOf(
                                "Address 1",
                                "Address 2",
                                "Address 3"
                            )
                        ),
                        SpinnerChoice(
                            type = "familySize",
                            choices = arrayListOf("Family size", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "parentStatus",
                            choices = arrayListOf("Parent status", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "mothersEducation",
                            choices = arrayListOf("Mother's education", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "fathersEducation",
                            choices = arrayListOf("Fathers education", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "mothersJob",
                            choices = arrayListOf("Mother's job", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "fathersJob",
                            choices = arrayListOf("Father's job", "1", "2", "3", "4+")
                        ),
                        SpinnerChoice(
                            type = "homeToSchoolTravelTime",
                            choices = arrayListOf(
                                "Home to school travel time",
                                "1",
                                "2",
                                "3",
                                "4+"
                            )
                        ),
                        SpinnerChoice(
                            type = "weeklyStudyTime",
                            hint = "Weekly study time",
                            choices = arrayListOf("1", "2", "3", "4+")
                        )
                    ).apply(userDetails)
                )
            }
        return list
    }

    private fun <V, T : Choice<V>> ArrayList<T>.apply(userDetails: UserDetails?): List<V> {
        return map {
            it.apply(userDetails)
        }
    }
}