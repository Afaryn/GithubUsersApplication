package com.example.githubusers.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.online.response.GitResponse
import com.example.githubusers.data.online.response.ItemsItem
import com.example.githubusers.data.online.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem?>?>()
    val listUser: LiveData<List<ItemsItem?>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_ID = "afa"
    }

    init {
        findUser(USER_ID)
    }

    fun findUser(query: String){
        _isLoading.value= true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GitResponse> {
            override fun onResponse(
                call: Call<GitResponse>,
                response: Response<GitResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value= response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}