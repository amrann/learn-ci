package com.dcdng.subms_3_2.core.data.source.remote.network


import com.dcdng.subms_3_2.core.data.source.remote.response.LoginResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.RegisterResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.StoriesResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.*

interface ApiService {
  @FormUrlEncoded
  @POST("register")
  fun register(
    @Field("name") name: String,
    @Field("email") email: String,
    @Field("password") password: String
  ): Call<RegisterResponse>

  @FormUrlEncoded
  @POST("login")
  fun login(
    @Field("email") email: String,
    @Field("password") password: String
  ): Call<LoginResponse>

  @GET("stories?location=1")
//  @GET("stories?location=0")
  fun listStories(): Call<StoriesResponse>


////    @GET("stories?location=1")
////    fun listAllStories(
////      @Query("page") page: Int,
////      @Query("size") size: Int
////    ): Call<StoriesResponse>
//  @GET("stories?location=1")
//  suspend fun listAllStories(
//    @Query("page") page: Int,
//    @Query("size") size: Int
//  ): StoriesResponse

  // default sbelumnya ini ni
  @GET("stories?location=1")
  suspend fun listAllStories(
    @Query("page") page: Int,
    @Query("size") size: Int
  ): StoriesResponse

//  @GET("stories?location=1")
//  suspend fun listAllStories(
//    @Header("Authorization") token: String,
//    @Query("page") page: Int,
//    @Query("size") size: Int
//  ): StoriesResponse

  @Multipart
  @POST("stories")
  suspend fun uploadImage(
//    @Header("Authorization") token: String,
    @Part file: MultipartBody.Part,
    @Part("description") description: RequestBody,
  ): StoryUploadResponse
}