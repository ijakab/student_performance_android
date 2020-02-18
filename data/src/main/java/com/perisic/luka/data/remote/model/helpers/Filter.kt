package com.perisic.luka.data.remote.model.helpers

data class FilterRequest(
    val filters: List<Filter>? = null,
    var page: Int? = 0,
    val keywords: List<String>? = null
)

data class Filter(
    val key: String,
    val values: String
)