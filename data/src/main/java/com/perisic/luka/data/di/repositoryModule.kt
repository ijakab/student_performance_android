package com.perisic.luka.data.di

import com.perisic.luka.data.repo.AuthRepository
import com.perisic.luka.data.repo.AuthRepositoryImpl
import com.perisic.luka.data.repo.UserRepository
import com.perisic.luka.data.repo.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

}