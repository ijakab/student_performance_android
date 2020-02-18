package com.perisic.luka.data.local.di

import androidx.room.Room
import com.perisic.luka.data.local.LocalDatabase
import com.perisic.luka.data.local.dao.TokenModelDao
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(
            get(),
            LocalDatabase::class.java,
            "studentPerformanceDb"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        TokenModelDao.create(get())
    }

}