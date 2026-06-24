package com.kotlin.workmanagercomposehilt

import com.kotlin.workmanagercomposehilt.data.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DemoService {

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<PostResponse>
}