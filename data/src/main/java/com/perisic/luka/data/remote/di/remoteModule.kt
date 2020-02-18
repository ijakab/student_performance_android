package com.perisic.luka.data.remote.di

import com.perisic.luka.data.remote.util.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(TokenInterceptor(get()))
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder().apply {
            baseUrl("https://api.student-performance.jsteam.gaussx.com/api/")
            addConverterFactory(GsonConverterFactory.create())
            client(get())
        }.build()

    }
}