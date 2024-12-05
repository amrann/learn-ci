package com.dcdng.subms_3_2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dcdng.subms_3_2.core.domain.usecase.FavoriteStoryUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val favoriteStoryUseCase: FavoriteStoryUseCase) :
  ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T =
    when {
      modelClass.isAssignableFrom(FavoriteStoryViewModel::class.java) -> {
        FavoriteStoryViewModel(favoriteStoryUseCase) as T
      }
      else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
    }
}