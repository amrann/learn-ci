package com.dcdng.subms_3_2.core.data.source.repo

import androidx.lifecycle.MutableLiveData
import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.core.data.pref.UserPreference
import com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource
import com.dcdng.subms_3_2.core.domain.irepository.IUserRepository
import com.dcdng.subms_3_2.core.domain.model.Login
import com.dcdng.subms_3_2.core.domain.model.Register
import com.dcdng.subms_3_2.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import com.dcdng.subms_3_2.core.utils.Result;
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
  private val userPreference: UserPreference,
  private val remoteDataSource: RemoteDataSource
) : IUserRepository {

  override suspend fun saveSession(user: UserModel) {
    userPreference.saveSession(user)
  }

  override fun getSession(): Flow<UserModel> {
    return userPreference.getSession()
  }

  override suspend fun logout() {
    userPreference.logout()
  }

  override fun messageResp() = remoteDataSource.msgResponse

  override fun getToken() = remoteDataSource.authToken

  override fun postDataRegis(name: String, email: String, password: String): MutableLiveData<Result<Register>> {
    val result = MutableLiveData<Result<Register>>()
    result.value = Result.Loading

    remoteDataSource.postDataRegis(name, email, password).observeForever { response ->
      result.value = when (response) {
        is Result.Success -> Result.Success(DataMapper.mapResponseRegisterToModel(response.data))
        is Result.Error -> Result.Error(response.error)
        Result.Loading -> Result.Loading
      }
    }
    return result
  }

  override fun postDataLogin(email: String, password: String): MutableLiveData<Result<Login>> {
    val result = MutableLiveData<Result<Login>>()
    result.value = Result.Loading

    remoteDataSource.postDataLogin(email, password).observeForever { response ->
      result.value = when (response) {
        is Result.Success -> Result.Success(DataMapper.mapResponseLoginToModel(response.data))
        is Result.Error -> Result.Error(response.error)
        Result.Loading -> Result.Loading
      }
    }
    return result
  }
}