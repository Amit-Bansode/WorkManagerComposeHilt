package com.kotlin.workmanagercomposehilt.di

import com.kotlin.workmanagercomposehilt.DemoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getOkHTTPClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }

    @Provides
    @Singleton
    fun getGSONConverterFactory(): GsonConverterFactory {
        val gsonConverterFactory = GsonConverterFactory.create()
        return gsonConverterFactory
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun getDemoService(retrofit: Retrofit): DemoService {
        val demoService = retrofit.create(DemoService::class.java)
        return demoService
    }

}