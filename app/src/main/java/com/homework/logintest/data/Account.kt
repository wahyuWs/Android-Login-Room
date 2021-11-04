package com.homework.logintest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Account(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "email")
    var email: String = "admin@gmail.com",

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null
)
