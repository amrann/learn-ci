package com.dcdng.subms_3_2.core.domain.usecase

import com.dcdng.subms_3_2.core.domain.irepository.IStoryRepository
import javax.inject.Inject

class StoryInteractor @Inject constructor(private val storyRepository: IStoryRepository): StoryUseCase {
  override fun getDataStories() = storyRepository.getDataStories()
  override fun getAllStories() = storyRepository.getAllStories()
}