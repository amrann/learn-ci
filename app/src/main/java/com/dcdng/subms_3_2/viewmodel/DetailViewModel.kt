package com.dcdng.subms_3_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val favoriteStoryUseCase: FavoriteStoryUseCase) : ViewModel() {
  val listFavoriteStories = favoriteStoryUseCase.getFavoriteStories().asLiveData()

  fun insertFavoriteStory(favoriteStory: FavoriteStory) = favoriteStoryUseCase.insertFavoriteStory(favoriteStory)

  fun deleteFavoriteStory(favoriteStory: FavoriteStory) = favoriteStoryUseCase.deleteFavoriteStory(favoriteStory)
}