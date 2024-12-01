package com.dcdng.subms_3_2.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dcdng.subms_3_2.core.domain.model.StoryList
import com.dcdng.subms_3_2.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
  fun getDataStories() : LiveData<Result<List<StoryList>>>
  fun getAllStories() : Flow<PagingData<StoryList>>
}