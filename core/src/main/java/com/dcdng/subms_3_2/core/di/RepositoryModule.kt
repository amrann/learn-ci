package com.dcdng.subms_3_2.core.di

import com.dcdng.subms_3_2.core.data.pref.UserPreference
import com.dcdng.subms_3_2.core.data.source.repo.FavoriteStoryRepository
import com.dcdng.subms_3_2.core.domain.irepository.IFavoriteStoryRepository
import com.dcdng.subms_3_2.core.domain.irepository.IUserRepository
import com.dcdng.subms_3_2.core.data.source.repo.UserRepository
import com.dcdng.subms_3_2.core.data.source.local.LocalDataSource
import com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource
import com.dcdng.subms_3_2.core.data.source.repo.StoryRepository
import com.dcdng.subms_3_2.core.domain.irepository.IStoryRepository
import com.dcdng.subms_3_2.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

  @Singleton
  @Provides
  fun provideUserRepository(
    userPreference: UserPreference,
    remoteDataSource: RemoteDataSource
  ): IUserRepository = UserRepository(userPreference, remoteDataSource)

  @Singleton
  @Provides
  fun provideFavoriteStoryRepository(
    localDataSource: LocalDataSource,
    appExecutors: AppExecutors
  ): IFavoriteStoryRepository = FavoriteStoryRepository(localDataSource, appExecutors)

  @Singleton
  @Provides
  fun provideStoryRepository(
    remoteDataSource: RemoteDataSource
  ): IStoryRepository = StoryRepository(remoteDataSource)

}