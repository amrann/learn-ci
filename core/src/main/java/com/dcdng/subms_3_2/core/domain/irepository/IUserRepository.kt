package com.dcdng.subms_3_2.core.domain.irepository

import androidx.lifecycle.MutableLiveData
import com.dcdng.subms_3_2.core.domain.model.Login
import com.dcdng.subms_3_2.core.domain.model.Register
import com.dcdng.subms_3_2.core.domain.model.StoryUpload
import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.core.utils.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IUserRepository {
  suspend fun saveSession(user: UserModel)
  fun getSession(): Flow<UserModel>
  suspend fun logout()
  fun messageResp(): MutableLiveData<String>
  fun getToken(): MutableLiveData<String>
  fun postDataRegis(name: String, email: String, password: String): MutableLiveData<Result<Register>>
  fun postDataLogin(email: String, password: String): MutableLiveData<Result<Login>>
  suspend fun postStoryUpload(multipartBody: MultipartBody.Part, requestBody: RequestBody): StoryUpload
}