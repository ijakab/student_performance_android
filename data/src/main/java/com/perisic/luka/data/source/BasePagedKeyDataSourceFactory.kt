package com.perisic.luka.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.perisic.luka.data.remote.model.helpers.FilterRequest
import com.perisic.luka.data.remote.model.helpers.FilterResponse
import com.perisic.luka.data.remote.util.BaseResponse
import retrofit2.Call

class BasePagedKeyDataSourceFactory<T>(
    private val apiSource: (FilterRequest) -> Call<BaseResponse<FilterResponse<T>>>,
    private val filterRequest: FilterRequest,
    private val status: MutableLiveData<BaseResponse.STATUS>
) : DataSource.Factory<Int, T>() {

    private lateinit var latestSource: BasePagedKeyedDataSource<T>
    private val sourceLiveData = MutableLiveData<BasePagedKeyedDataSource<T>>()

    override fun create(): DataSource<Int, T> {

        latestSource = BasePagedKeyedDataSource(
            apiSource, filterRequest, status
        )

        sourceLiveData.postValue(latestSource)

        return latestSource
    }
}