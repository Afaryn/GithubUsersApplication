package com.example.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.newsapp.utils.AppExecutors

import com.example.githubusers.data.local.FavUser
import com.example.githubusers.data.local.FavUserDao
import java.util.*

class FavUserRespository private constructor(
    private val favUserDao: FavUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavUser>>>()

    fun insertFavuser(username:FavUser){
      appExecutors.diskIO.execute{
          favUserDao.insertFavuser(username)
      }
        val localData = favUserDao.getAllFavUser()
        result.addSource(localData) { newData: List<FavUser> ->
            result.value = Result.Success(newData)
        }
    }

    fun getAllFavUser(): LiveData<List<FavUser>> {
        return favUserDao.getAllFavUser()
    }

    fun getFavUserByUsername(username:String):LiveData<FavUser> {
        return favUserDao.getFavUserByUsername(username)
    }

    fun deleteFavUser(favUser: FavUser){
        appExecutors.diskIO.execute{
            favUserDao.deleteFavUser(favUser)
        }
    }

    companion object {
        @Volatile
        private var instance: FavUserRespository? = null
        fun getInstance(
            newsDao: FavUserDao,
            appExecutors: AppExecutors
        ): FavUserRespository =
            instance ?: synchronized(this) {
                instance ?: FavUserRespository(newsDao, appExecutors)
            }.also { instance = it }
    }
}