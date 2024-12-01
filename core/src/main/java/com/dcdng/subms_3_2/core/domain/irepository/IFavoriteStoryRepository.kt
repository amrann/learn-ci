package com.dcdng.subms_3_2.core.domain.irepository

import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import kotlinx.coroutines.flow.Flow

interface IFavoriteStoryRepository {
  fun getFavoriteStories(): Flow<List<FavoriteStory>>
  fun insertFavoriteStory(favoriteStory: FavoriteStory)
  fun deleteFavoriteStory(favoriteStory: FavoriteStory)
}