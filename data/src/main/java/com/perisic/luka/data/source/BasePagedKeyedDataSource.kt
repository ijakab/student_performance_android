package com.perisic.luka.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.perisic.luka.data.remote.model.helpers.FilterRequest
import com.perisic.luka.data.remote.model.helpers.FilterResponse
import com.perisic.luka.data.remote.util.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasePagedKeyedDataSource<T>(
    private val apiSource: (FilterRequest) -> Call<BaseResponse<FilterResponse<T>>>,
    private val filterRequest: FilterRequest,
    private val status: MutableLiveData<BaseResponse.STATUS>
) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        filterRequest.page = 1
        apiSource(filterRequest).enqueueFilter {
            callback.onResult(
                it.data,
                null,
                if (it.pagination.page != it.pagination.lastPage)
                    (params.requestedLoadSize % (it.pagination.perPage)) + 1
                else null
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        filterRequest.page = params.key
        apiSource(filterRequest).enqueueFilter {
            callback.onResult(
                it.data,
                if (it.pagination.page != it.pagination.lastPage)
                    params.key + 1
                else null
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}

    private fun Call<BaseResponse<FilterResponse<T>>>.enqueueFilter(
        callback: (FilterResponse<T>) -> Unit
    ) {
        status.postValue(BaseResponse.STATUS.LOADING)
        enqueue(
            object : Callback<BaseResponse<FilterResponse<T>>> {
                override fun onFailure(call: Call<BaseResponse<FilterResponse<T>>>, t: Throwable) {
                    status.postValue(BaseResponse.STATUS.ERROR)
                }

                override fun onResponse(
                    call: Call<BaseResponse<FilterResponse<T>>>,
                    response: Response<BaseResponse<FilterResponse<T>>>
                ) {
                    if (response.isSuccessful) {
                        val body: BaseResponse<FilterResponse<T>>? = response.body()
                        if (body?.data != null) {
                            callback(body.data)
                            status.postValue(BaseResponse.STATUS.SUCCESS)
                        } else {
                            status.postValue(BaseResponse.STATUS.ERROR)
                        }
                    } else {
                        status.postValue(BaseResponse.STATUS.ERROR)
                    }
                }
            }
        )
    }

}