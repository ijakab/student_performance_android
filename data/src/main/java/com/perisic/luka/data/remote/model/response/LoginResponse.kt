package com.perisic.luka.data.remote.model.response

import com.perisic.luka.data.remote.model.helpers.User

data class LoginResponse(
    val user: User,
    val token: String
)