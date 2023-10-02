package com.example.githubusers.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class], version = 1, exportSchema = false)
abstract class FavUserRoomDatabase: RoomDatabase() {
    companion object{
        @Volatile
        var INSTANCE:FavUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):FavUserRoomDatabase{
            if (INSTANCE==null){
                synchronized(FavUserRoomDatabase::class){
                    INSTANCE=Room.databaseBuilder(context.applicationContext,FavUserRoomDatabase::class.java,"fav_database").build()
                }
            }
            return INSTANCE as FavUserRoomDatabase
        }

    }
    abstract fun favUserDao(): FavUserDao
}