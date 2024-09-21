package com.devrhyan.githubsearch.services.data

import com.devrhyan.githubsearch.models.UserItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("users/{user}/repos")
    suspend fun getPosts(
        @Path("user") user : String
    ) : Response<List<UserItem>>

    companion object {
        private val retrofitService : RetrofitService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RetrofitService::class.java)
        }

        fun getServiceInstance() : RetrofitService {
            return retrofitService
        }
    }
}