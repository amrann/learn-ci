package com.dcdng.subms_3_2.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dcdng.subms_3_2.core.data.source.remote.TokenProvider
import com.dcdng.subms_3_2.core.domain.usecase.StoryUseCase
import com.dcdng.subms_3_2.viewmodel.StoryViewModel
import javax.inject.Inject


class StoryViewModelFactory @Inject constructor(
  private val storyUseCase: StoryUseCase,
  private val tokenProvider: TokenProvider
  ) :
  ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
      return StoryViewModel(storyUseCase, tokenProvider) as T
    }
    throw IllegalArgumentException("ViewModel class tidak ditemukan: "+modelClass.name)
  }
}