package com.dcdng.subms_3_2.core.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

  fun getApiService(token: String? = null): ApiService {
    val loggingInterceptor =
      HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val authInterceptor = Interceptor { chain ->
      val req = chain.request()
      val requestHeaders = req.newBuilder()
        .addHeader("Authorization", "Bearer $token")
        .build()
      chain.proceed(requestHeaders)
    }

    val client = OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .addInterceptor(authInterceptor)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://story-api.dicoding.dev/v1/")
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
    return retrofit.create(ApiService::class.java)
  }

//  fun getApiServiceWithoutToken(): ApiService {
//    val loggingInterceptor =
//      HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    val client = OkHttpClient.Builder()
//      .addInterceptor(loggingInterceptor)
//      .build()
//    val retrofit = Retrofit.Builder()
//      .baseUrl("https://story-api.dicoding.dev/v1/")
//      .addConverterFactory(GsonConverterFactory.create())
//      .client(client)
//      .build()
//    return retrofit.create(ApiService::class.java)
//  }
}