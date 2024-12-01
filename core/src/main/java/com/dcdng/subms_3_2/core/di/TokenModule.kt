package com.dcdng.subms_3_2.core.di

import androidx.lifecycle.MutableLiveData
import com.dcdng.subms_3_2.core.data.source.remote.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {
  @Provides
  @Singleton
  fun provideTokenProvider(): TokenProvider {
    return object : TokenProvider {
      private val authToken = MutableLiveData("")

      override fun getToken(): String? {
        return authToken.value
      }

      override fun updateToken(token: String) {
        authToken.postValue(token)
      }
    }
  }
}