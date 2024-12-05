package com.dcdng.subms_3_2.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcdng.subms_3_2.core.data.StoriesPagingSource
import com.dcdng.subms_3_2.core.data.source.remote.response.LoginResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.RegisterResponse
import com.dcdng.subms_3_2.core.data.source.remote.network.ApiService
import com.dcdng.subms_3_2.core.data.source.remote.response.StoriesResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryListResponse
import com.dcdng.subms_3_2.core.utils.Result
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
  val msgResponse = MutableLiveData("")
  val authToken = MutableLiveData("")
  private val resultRegis = MutableLiveData<Result<RegisterResponse>>()
  private val resultLogin = MutableLiveData<Result<LoginResponse>>()
  private val resultDataStories = MediatorLiveData<Result<List<StoryListResponse>>>()


  fun postDataLogin(email: String, password: String): MutableLiveData<Result<LoginResponse>> {
    resultLogin.postValue(Result.Loading)

    val resp = apiService.login(email, password)
    resp.enqueue(object : Callback<LoginResponse> {
      override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        if (response.isSuccessful) {
          Log.e("REMOTEDATASOURCE", "response.body() : ${response.body()}")
          msgResponse.value = response.body()?.message
          authToken.value = response.body()?.loginResult?.token
          response.body()?.let { loginResponse ->
            resultLogin.postValue(Result.Success(loginResponse))
          } ?: run {
            resultLogin.postValue(Result.Error("Empty response body"))
          }
        } else {
          val errorMessage = response.errorBody()?.string()?.let {
            try {
              val errResponse = JSONObject(it)
              errResponse.getString("message")
            } catch (e: Exception) {
              "Unknown error"
            }
          } ?: "Error without body"
          msgResponse.value = errorMessage
          resultLogin.postValue(Result.Error(errorMessage))
        }
      }

      override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        resultLogin.postValue(Result.Error(t.message ?: "Unknown failure"))
      }
    })
    return resultLogin
  }

  fun postDataRegis(name: String, email: String, password: String): MutableLiveData<Result<RegisterResponse>> {
    resultRegis.postValue(Result.Loading)

    val resp = apiService.register(name, email, password)
    resp.enqueue(object : Callback<RegisterResponse> {
      override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
        if (response.isSuccessful) {
          msgResponse.value = response.body()?.message
          response.body()?.let { loginResponse ->
            resultRegis.postValue(Result.Success(loginResponse))
          } ?: run {
            resultRegis.postValue(Result.Error("Empty response body"))
          }
        } else {
          val errorMessage = response.errorBody()?.string()?.let {
            try {
              val errResponse = JSONObject(it)
              errResponse.getString("message")
            } catch (e: Exception) {
              "Unknown error"
            }
          } ?: "Error without body"
          msgResponse.value = errorMessage
          resultRegis.postValue(Result.Error(errorMessage))
        }
      }
      override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
        Log.e("CEKKK", "masuk onFailure")
        resultRegis.value = Result.Error(t.message.toString())
      }
    })
    return resultRegis
  }

  // MapsActivity
  fun getDataStories() : LiveData<Result<List<StoryListResponse>>> {
    resultDataStories.postValue(Result.Loading)

    val resp = apiService.listStories()
    resp.enqueue(object : Callback<StoriesResponse> {
      override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
        if (response.isSuccessful) {
          val data = response.body()?.listStory
          val listData = ArrayList<StoryListResponse>()
          if (data != null) {
            for (dt in data) {
              val dtList = StoryListResponse(
                dt.id,
                dt.name,
                dt.description,
                dt.photoUrl,
                dt.createdAt,
                dt.lon,
                dt.lat
              )
              listData.add(dtList)
            }
          }
          resultDataStories.value = Result.Success(listData)
        }
      }

      override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
        resultDataStories.value = Result.Error(t.message.toString())
      }
    })
    return resultDataStories
  }

  // MainActivity
  fun getAllStories(): Flow<PagingData<StoryListResponse>> {
    return Pager(
      config = PagingConfig(
        pageSize = 5
      ),
      pagingSourceFactory = {
        StoriesPagingSource(apiService)
      }
    ).flow
  }
}