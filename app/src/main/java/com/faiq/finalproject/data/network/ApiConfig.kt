package com.faiq.finalproject.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getApiService(): ApiService {
        val httpLoggingInterceptor: HttpLoggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .pingInterval(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}