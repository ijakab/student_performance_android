package com.perisic.luka.data.remote.model.helpers

data class FilterResponse<T>(
    val pagination: Pagination,
    val data: List<T>
)