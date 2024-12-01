package com.dcdng.subms_3_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.dcdng.subms_3_2.core.data.source.remote.TokenProvider
import com.dcdng.subms_3_2.core.domain.model.StoryList
import com.dcdng.subms_3_2.core.domain.usecase.StoryUseCase
import kotlinx.coroutines.flow.Flow

class StoryViewModel (
  private val storyUseCase: StoryUseCase,
  private val tokenProvider: TokenProvider
) : ViewModel() {

  // MainActivity
  val dataPagingStories: Flow<PagingData<StoryList>> = storyUseCase.getAllStories()

  // MapsActivity
  fun getDataStories() = storyUseCase.getDataStories()

  fun updateToken(token: String) {
    tokenProvider.updateToken(token)
  }

}