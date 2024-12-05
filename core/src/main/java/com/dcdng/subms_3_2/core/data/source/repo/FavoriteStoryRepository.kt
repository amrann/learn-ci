package com.dcdng.subms_3_2.core.data.source.repo

import com.dcdng.subms_3_2.core.data.source.local.LocalDataSource
import com.dcdng.subms_3_2.core.domain.irepository.IFavoriteStoryRepository
import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import com.dcdng.subms_3_2.core.utils.AppExecutors
import com.dcdng.subms_3_2.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteStoryRepository @Inject constructor(
  private val localDataSource: LocalDataSource,
  private val appExecutors: AppExecutors
) : IFavoriteStoryRepository {

  override fun getFavoriteStories(): Flow<List<FavoriteStory>> {
    return localDataSource.getFavoriteStories().map {
      DataMapper.mapEntityListFavoriteStoryToModel(it)
    }
  }

  override fun insertFavoriteStory(favoriteStory: FavoriteStory) {
    val dataEntity = DataMapper.mapModelFavoriteStoryToEntity(favoriteStory)
    appExecutors.diskIO().execute {localDataSource.insertFavoriteStory(dataEntity)}
  }

  override fun deleteFavoriteStory(favoriteStory: FavoriteStory) {
    val dataEntity = DataMapper.mapModelFavoriteStoryToEntity(favoriteStory)
    appExecutors.diskIO().execute { localDataSource.deleteFavoriteStory(dataEntity) }
  }
}