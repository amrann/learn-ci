package com.dcdng.subms_3_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

  fun getSession(): LiveData<UserModel> {
    return userUseCase.getSession().asLiveData()
  }

  fun logout() {
    viewModelScope.launch {
      userUseCase.logout()
    }
  }
}