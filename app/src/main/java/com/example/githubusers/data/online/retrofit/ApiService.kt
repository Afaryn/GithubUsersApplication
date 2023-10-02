package com.example.githubusers.data.online.retrofit

import com.example.githubusers.data.online.response.DetailUserResponse
import com.example.githubusers.data.online.response.GitResponse
import com.example.githubusers.data.online.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.*



interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<GitResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}