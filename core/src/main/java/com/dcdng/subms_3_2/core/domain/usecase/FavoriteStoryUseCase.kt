package com.dcdng.subms_3_2.core.domain.usecase

import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import kotlinx.coroutines.flow.Flow

interface FavoriteStoryUseCase {
  fun getFavoriteStories(): Flow<List<FavoriteStory>>
  fun insertFavoriteStory(favoriteStory: FavoriteStory)
  fun deleteFavoriteStory(favoriteStory: FavoriteStory)
}