package com.example.uas_ppapba_syafiqabdillahhabib_514688.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "role")
    val role: String // "ADMIN" atau "USER"
)
