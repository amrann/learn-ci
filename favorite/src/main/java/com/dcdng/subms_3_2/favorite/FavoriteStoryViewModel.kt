package com.dcdng.subms_3_2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryUseCase

class FavoriteStoryViewModel (favoriteStoryUseCase: FavoriteStoryUseCase) : ViewModel() {
//  val listFavoriteStories = favoriteStoryUseCase.getFavoriteStories()
  val listFavoriteStories = favoriteStoryUseCase.getFavoriteStories().asLiveData()
}
