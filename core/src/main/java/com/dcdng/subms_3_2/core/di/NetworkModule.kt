package com.dcdng.subms_3_2.core.di

import android.util.Log
import com.dcdng.subms_3_2.core.data.source.remote.network.ApiService
import com.dcdng.subms_3_2.core.data.source.remote.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideBaseUrl() = "https://story-api.dicoding.dev/v1/"

  @Provides
  @Singleton
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun provideAuthInterceptor(tokenProvider: TokenProvider): Interceptor {
    return Interceptor { chain ->
      val token = tokenProvider.getToken()
      Log.e("NETWORK", "authToken 3 => $token")
      val req = chain.request()
      val requestHeaders = req.newBuilder()
        .addHeader("Authorization", "Bearer $token")
        .build()
      chain.proceed(requestHeaders)
    }
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: Interceptor
  ): OkHttpClient {
    val hostname = "story-api.dicoding.dev"
    val certificatePinner = CertificatePinner.Builder()
      .add(hostname, "sha256/imap7Ijane1+4/4o1/qrrbdk3wQcbbU3BDMi/PRlyOU=")
      .add(hostname, "sha256/K7rZOrXHknnsEhUH8nLL4MZkejquUuIvOIr6tCa0rbo=")
      .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
      .build()
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .addInterceptor(authInterceptor)
      .certificatePinner(certificatePinner)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    client: OkHttpClient,
    baseUrl: String
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }
}