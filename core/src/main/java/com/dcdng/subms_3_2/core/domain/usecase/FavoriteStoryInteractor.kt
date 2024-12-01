package com.dcdng.subms_3_2.core.domain.usecase

import com.dcdng.subms_3_2.core.domain.irepository.IFavoriteStoryRepository
import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteStoryInteractor @Inject constructor(private val favoriteStoryRepository: IFavoriteStoryRepository):
  FavoriteStoryUseCase {
  override fun getFavoriteStories(): Flow<List<FavoriteStory>> = favoriteStoryRepository.getFavoriteStories()
  override fun insertFavoriteStory(favoriteStory: FavoriteStory) = favoriteStoryRepository.insertFavoriteStory(favoriteStory)
  override fun deleteFavoriteStory(favoriteStory: FavoriteStory) = favoriteStoryRepository.deleteFavoriteStory(favoriteStory)

  }