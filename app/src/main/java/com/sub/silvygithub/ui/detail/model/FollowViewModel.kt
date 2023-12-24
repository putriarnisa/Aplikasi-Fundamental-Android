package com.sub.silvygithub.ui.detail.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.data.remote.retrofit.ApiConfig
import com.sub.silvygithub.data.remote.retrofit.ApiHelper

class FollowViewModel: ViewModel() {

    private val _getFollower = MutableLiveData<List<UserItems>>()
    val getFollower: LiveData<List<UserItems>> = _getFollower

    private val _getFollowing = MutableLiveData<List<UserItems>>()
    val getFollowing: LiveData<List<UserItems>> = _getFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollower(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(name)
        ApiHelper.makeApiCall(client, _getFollower, _isLoading)
    }

    fun getFollowing(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(name)
        ApiHelper.makeApiCall(client, _getFollowing, _isLoading)
    }
}
