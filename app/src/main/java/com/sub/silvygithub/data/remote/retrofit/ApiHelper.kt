package com.sub.silvygithub.data.remote.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiHelper {
    fun <T> makeApiCall(client: Call<T>, liveData: MutableLiveData<T>, isLoading: MutableLiveData<Boolean>) {
        isLoading.value = true
        client.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    liveData.value = response.body()
                } else {
                    Log.e("ApiHelper", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                isLoading.value = false
                Log.e("ApiHelper", "onFailure: ${t.message.toString()}")
            }
        })
    }
}