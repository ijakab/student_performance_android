package com.perisic.luka.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey val id: Int = 1,
    val token: String?,
    val role: String?
)