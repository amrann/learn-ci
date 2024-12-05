package com.dcdng.subms_3_2.core.di

import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

  fun favoriteStoryUseCase(): FavoriteStoryUseCase
}