package com.dcdng.subms_3_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

  fun saveSession(user: UserModel) {
    viewModelScope.launch {
      userUseCase.saveSession(user)
    }
  }

  fun messageResp() = userUseCase.messageResp()

  fun getToken() = userUseCase.getToken()

  fun postDataLogin(email: String, password: String) = userUseCase.postDataLogin(email, password)

}