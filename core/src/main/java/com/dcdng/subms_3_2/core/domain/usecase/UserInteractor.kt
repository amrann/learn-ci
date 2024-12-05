package com.dcdng.subms_3_2.core.domain.usecase;

import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.core.domain.irepository.IUserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {

  override suspend fun saveSession(user: UserModel) = userRepository.saveSession(user)
  override fun getSession() = userRepository.getSession()
  override suspend fun logout() = userRepository.logout()
  override fun messageResp() = userRepository.messageResp()
  override fun getToken() = userRepository.getToken()
  override fun postDataRegis(
    name: String,
    email: String,
    password: String
  ) = userRepository.postDataRegis(name, email, password)
  override fun postDataLogin(
    email: String,
    password: String
  ) = userRepository.postDataLogin(email, password)

}
