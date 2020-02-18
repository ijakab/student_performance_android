package com.perisic.luka.data.model

import com.perisic.luka.data.remote.model.helpers.UserDetails

abstract class Choice<T>(
    val type: String
) {

    abstract fun apply(userDetails: UserDetails?): T

}