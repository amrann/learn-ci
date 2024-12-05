package com.dcdng.subms_3_2.core.data.source.local

import com.dcdng.subms_3_2.core.data.source.local.entity.FavoriteStoryEntity
import com.dcdng.subms_3_2.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val favoriteDao: FavoriteDao) {

  fun getFavoriteStories(): Flow<List<FavoriteStoryEntity>> = favoriteDao.getFavoriteStories()

  fun insertFavoriteStory(dataStory: FavoriteStoryEntity) = favoriteDao.insertFavoriteStory(dataStory)

  fun deleteFavoriteStory(dataStory: FavoriteStoryEntity) = favoriteDao.deleteFavoriteStory(dataStory)

}