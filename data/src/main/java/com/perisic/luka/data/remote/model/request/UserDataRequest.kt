package com.perisic.luka.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class UserDataRequest(
    val username: String,
    val password: String? = null,
    val email: String,
    val role: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String
)