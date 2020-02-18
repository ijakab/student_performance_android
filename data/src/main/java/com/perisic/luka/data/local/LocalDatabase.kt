package com.perisic.luka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perisic.luka.data.local.dao.TokenModelDao
import com.perisic.luka.data.local.model.Token

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Token::class
    ]
)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun tokenModelDao(): TokenModelDao

}