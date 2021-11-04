package com.homework.logintest.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homework.logintest.data.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(userAccount: Account)

    @Query("SELECT * FROM account WHERE email = :email AND password = :password")
    fun authenticate(email: String, password: String): Account

    @Query("SELECT * FROM account WHERE email = :email")
    fun checkEmail(email: String): Account
}