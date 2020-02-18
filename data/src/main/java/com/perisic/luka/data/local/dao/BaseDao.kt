package com.perisic.luka.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(vararg dataList: T)

    @Delete
    fun delete(data: T)

    @Delete
    fun deleteList(vararg dataList: T)

}