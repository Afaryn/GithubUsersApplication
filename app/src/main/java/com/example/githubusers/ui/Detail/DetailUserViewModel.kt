package com.example.githubusers.ui.Detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.response.DetailUserResponse
import com.example.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel:ViewModel() {

    val user = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUserDetail(username:String){
        _isLoading.value=true
        ApiConfig.getApiService().getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.e("Failure", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure","onFailure: ${t.message.toString()}")
                }
            })
            }
    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }

    }
