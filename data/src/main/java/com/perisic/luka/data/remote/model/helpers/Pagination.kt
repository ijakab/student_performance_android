package com.perisic.luka.data.remote.model.helpers

data class Pagination(
    val total: Int,
    val perPage: Int,
    val page: Int,
    val lastPage: Int
)