package com.example.uas_ppapba_syafiqabdillahhabib_514688.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {
    @Query("SELECT * FROM 'users' WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM 'users' WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}