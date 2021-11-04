package com.homework.logintest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.logintest.data.Account

@Database(entities = [Account::class], version = 1)
 abstract class AccountRoomDatabase: RoomDatabase() {

     abstract fun accountDao(): AccountDao

     companion object {
         @Volatile
         var INSTANCE: AccountRoomDatabase? = null

         @JvmStatic
         fun getDatabase(context: Context): AccountRoomDatabase {
             if (INSTANCE == null) {
                 synchronized(AccountRoomDatabase::class.java) {
                     INSTANCE = Room.databaseBuilder(context.applicationContext,
                         AccountRoomDatabase::class.java, "data_account")
                         .build()
                 }
             }

             return INSTANCE as AccountRoomDatabase
         }
     }
}