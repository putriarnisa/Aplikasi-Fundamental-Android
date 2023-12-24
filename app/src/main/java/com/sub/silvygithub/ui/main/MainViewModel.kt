package com.sub.silvygithub.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.data.remote.response.UserResponse
import com.sub.silvygithub.data.remote.retrofit.ApiConfig
import com.sub.silvygithub.data.remote.retrofit.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _getUser = MutableLiveData<List<UserItems>>()
    val getUser: LiveData<List<UserItems>> = _getUser

    private val _getUserSearch = MutableLiveData<List<UserItems>>()
    val getUserSearch: LiveData<List<UserItems>> = _getUserSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser()
        ApiHelper.makeApiCall(client, _getUser, _isLoading)
    }

    fun getUserSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object: Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getUserSearch.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }
        })
    }
}
