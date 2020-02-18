package com.perisic.luka.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.perisic.luka.data.remote.util.BaseResponse

data class PagedData<T>(
    val pagedList: LiveData<PagedList<T>>,
    val status: LiveData<BaseResponse.STATUS>
)