package com.example.githubusers.ui.fav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubusers.data.local.FavUserDao
import com.example.githubusers.data.local.FavUserRoomDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.FavUserRespository
import com.example.githubusers.data.local.FavUser

class FavViewModel(private val favUserRespository: FavUserRespository): ViewModel() {

    fun getAllFavUser()=favUserRespository.getAllFavUser()

    fun getFavUserByUsername(username:String): LiveData<FavUser> = favUserRespository.getFavUserByUsername(username)

    fun insertFavuser(favUser: FavUser){
        favUserRespository.insertFavuser(favUser)
    }

    fun deleteFavUser(favUser: FavUser){
        favUserRespository.deleteFavUser(favUser)
    }


}