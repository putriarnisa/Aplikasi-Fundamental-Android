package com.sub.silvygithub.ui.detail.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sub.silvygithub.data.remote.response.UserItemDetail
import com.sub.silvygithub.data.remote.retrofit.ApiConfig
import com.sub.silvygithub.data.remote.retrofit.ApiHelper

class DetailViewModel : ViewModel() {

    private val _getDetail = MutableLiveData<UserItemDetail>()
    val getDetail: LiveData<UserItemDetail> = _getDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        ApiHelper.makeApiCall(client, _getDetail, _isLoading)
    }

    companion object {
        const val USERNAME = "Username"
    }
}