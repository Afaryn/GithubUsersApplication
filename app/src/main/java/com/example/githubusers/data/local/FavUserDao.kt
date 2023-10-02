package com.example.githubusers.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubusers.data.local.FavUser


@Dao
interface FavUserDao {

    @Query("SELECT * FROM FavUser ORDER BY username DESC")
    fun getAllFavUser(): LiveData<List<FavUser>>

    @Query("SELECT * FROM favuser WHERE favuser.username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavuser(favUser: FavUser)

    @Delete
    fun deleteFavUser(favUser: FavUser)




}