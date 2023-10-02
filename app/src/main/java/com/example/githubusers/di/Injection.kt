package com.example.githubusers.di

import android.content.Context
import com.dicoding.newsapp.utils.AppExecutors
import com.example.githubusers.data.FavUserRespository
import com.example.githubusers.data.local.FavUserRoomDatabase
import com.example.githubusers.data.online.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavUserRespository {
        val database = FavUserRoomDatabase.getDatabase(context)
        val dao = database.favUserDao()
        val appExecutors = AppExecutors()
        return FavUserRespository.getInstance(dao, appExecutors)
    }
}