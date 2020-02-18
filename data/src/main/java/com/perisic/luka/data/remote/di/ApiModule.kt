package com.perisic.luka.data.remote.di

import com.perisic.luka.data.remote.api.AuthService
import com.perisic.luka.data.remote.api.UserService
import org.koin.dsl.module

val apiModule = module {

    single {
        AuthService.create(get())
    }

    single {
        UserService.create(get())
    }

}