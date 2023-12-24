package com.sub.silvygithub.data.remote.retrofit

import com.sub.silvygithub.data.remote.response.UserItems
import com.sub.silvygithub.data.remote.response.UserResponse
import com.sub.silvygithub.data.remote.response.UserItemDetail
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUser(): Call<List<UserItems>>

    @GET("search/users")
    fun getSearchUser(@Query("q") q: String): Call<UserResponse>

    @GET("users/{name}")
    fun getUserDetail(@Path("name") name: String) : Call<UserItemDetail>

    @GET("users/{name}/following")
    fun getFollowing(@Path("name") name: String) : Call<List<UserItems>>

    @GET("users/{name}/followers")
    fun getFollower(@Path("name") name: String): Call<List<UserItems>>
}