package com.perisic.luka.data.remote.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Reader

fun <T> Call<BaseResponse<T>>.makeCall(extraCallback: (BaseResponse<T>) -> Any? = {}): LiveData<BaseResponse<T>> {

    val responseLiveData = MutableLiveData<BaseResponse<T>>(BaseResponse())

    enqueue(object : Callback<BaseResponse<T>> {

        override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
            responseLiveData.value = BaseResponse(
                message = t.message,
                status = BaseResponse.STATUS.ERROR
            )
        }

        override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
            if (response.isSuccessful) {
                val body: BaseResponse<T>? = response.body()
                if (body != null) {
                    extraCallback(body)
                    responseLiveData.value = BaseResponse(body, BaseResponse.STATUS.SUCCESS)
                } else {
                    responseLiveData.value = BaseResponse(
                        message = BaseResponse.ERROR.UNKNOWN.name,
                        status = BaseResponse.STATUS.ERROR
                    )
                }
            } else {
                val errorResponse: BaseResponse<T>? = try {
                    response.errorBody()?.charStream()?.parse()
                } catch (e: Exception) {
                    BaseResponse(
                        message = BaseResponse.ERROR.UNKNOWN.name,
                        status = BaseResponse.STATUS.ERROR
                    )
                }
                responseLiveData.value = BaseResponse(
                    baseResponse = errorResponse,
                    status = BaseResponse.STATUS.ERROR
                )
            }
        }

    })
    return responseLiveData
}

inline fun <reified T : BaseResponse<*>> Reader.parse(): T {
    try {
        return Gson().fromJson<T>(this, T::class.java)
    } catch (e: JsonSyntaxException) {
        throw RuntimeException()
    }
}