package com.dcdng.subms_3_2.di;

import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryInteractor
import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryUseCase
import com.dcdng.subms_3_2.core.domain.usecase.StoryInteractor
import com.dcdng.subms_3_2.core.domain.usecase.StoryUseCase
import com.dcdng.subms_3_2.core.domain.usecase.UserInteractor
import com.dcdng.subms_3_2.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module;
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

  @Binds
  @Singleton
  abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase

  @Binds
  @Singleton
  abstract fun provideFavoriteStoryUseCase(favoriteStoryInteractor: FavoriteStoryInteractor): FavoriteStoryUseCase

  @Binds
  @Singleton
  abstract fun provideStoryUseCase(storyInteractor: StoryInteractor): StoryUseCase
}
