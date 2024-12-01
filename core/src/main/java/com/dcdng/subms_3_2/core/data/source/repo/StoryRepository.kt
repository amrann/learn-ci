package com.dcdng.subms_3_2.core.data.source.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource
import com.dcdng.subms_3_2.core.domain.irepository.IStoryRepository
import com.dcdng.subms_3_2.core.domain.model.StoryList
import com.dcdng.subms_3_2.core.utils.DataMapper
import com.dcdng.subms_3_2.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) : IStoryRepository {

  // MapsActivity
  override fun getDataStories() : LiveData<Result<List<StoryList>>> {
    val result = MutableLiveData<Result<List<StoryList>>>()
    result.value = Result.Loading

    remoteDataSource.getDataStories().observeForever { response ->
      result.value = when (response) {
        is Result.Success -> Result.Success(DataMapper.mapResponseListStoryToModel(response.data))
        is Result.Error -> Result.Error(response.error)
        Result.Loading -> Result.Loading
      }
    }
    return result
  }

  // MainActivity
  override fun getAllStories() : Flow<PagingData<StoryList>> {
    return remoteDataSource.getAllStories().map {
      DataMapper.mapResponsePagingStoryToModel(it)
    }
  }
}
