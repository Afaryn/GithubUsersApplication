package com.example.githubusers.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavUser")
class FavUser(

    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    var username: String="",

    @field:ColumnInfo(name = "urlAvatar")
    var urlAvatar: String? = null,
)