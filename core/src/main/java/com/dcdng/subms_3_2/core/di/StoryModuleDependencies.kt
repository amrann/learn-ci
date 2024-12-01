package com.dcdng.subms_3_2.core.di

import com.dcdng.subms_3_2.core.data.source.remote.TokenProvider
import com.dcdng.subms_3_2.core.domain.usecase.StoryUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface StoryModuleDependencies {
  fun storyUseCase(): StoryUseCase
  fun tokenProvider(): TokenProvider
}