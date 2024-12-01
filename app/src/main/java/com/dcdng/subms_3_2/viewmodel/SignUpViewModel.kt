package com.dcdng.subms_3_2.viewmodel

import androidx.lifecycle.ViewModel
import com.dcdng.subms_3_2.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

  fun messageResp() = userUseCase.messageResp();

  fun postDataRegis(name: String, email: String, password: String) = userUseCase.postDataRegis(name, email, password)

}