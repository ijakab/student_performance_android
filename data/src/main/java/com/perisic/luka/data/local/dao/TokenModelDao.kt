package com.perisic.luka.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.perisic.luka.data.local.LocalDatabase
import com.perisic.luka.data.local.model.Token

@Dao
interface TokenModelDao: BaseDao<Token> {

    @Query("SELECT * FROM tokens LIMIT 1")
    fun fetchTokenSync(): Token

    @Query("SELECT * FROM tokens LIMIT 1")
    fun fetchTokenAsync(): LiveData<Token>

    @Query("DELETE FROM tokens")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM tokens")
    fun tokenCount(): LiveData<Int>

    companion object {
        fun create(localDatabase: LocalDatabase): TokenModelDao = localDatabase.tokenModelDao()
    }

}