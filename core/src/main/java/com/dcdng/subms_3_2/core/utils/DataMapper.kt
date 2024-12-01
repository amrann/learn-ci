package com.dcdng.subms_3_2.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryListResponse
import com.dcdng.subms_3_2.core.data.source.local.entity.FavoriteStoryEntity
import com.dcdng.subms_3_2.core.data.source.remote.response.LoginResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.LoginResultResponse
import com.dcdng.subms_3_2.core.data.source.remote.response.RegisterResponse
import com.dcdng.subms_3_2.core.domain.model.FavoriteStory
import com.dcdng.subms_3_2.core.domain.model.Login
import com.dcdng.subms_3_2.core.domain.model.LoginResult
import com.dcdng.subms_3_2.core.domain.model.Register
import com.dcdng.subms_3_2.core.domain.model.StoryList

object DataMapper {

  fun mapResponseStoryListToModel(input: StoryListResponse) =
    FavoriteStory(
      id = input.id,
      name = input.name!!,
      description = input.description ?: "",
      photoUrl = input.photoUrl ?: "",
      createdAt = input.createdAt ?: "",
      lon = input.lon,
      lat = input.lat
    )

  fun mapEntityListFavoriteStoryToModel(input: List<FavoriteStoryEntity>): List<FavoriteStory> =
    input.map {
      FavoriteStory(
        id = it.id,
        name = it.name,
        description = it.description,
        photoUrl = it.photoUrl,
        createdAt = it.createdAt,
        lon = it.lon,
        lat = it.lat
      )
    }

  fun mapResponseListStoryToModel(input: List<StoryListResponse>): List<StoryList> =
    input.map {
      StoryList(
        id = it.id,
        name = it.name!!,
        description = it.description!!,
        photoUrl = it.photoUrl!!,
        createdAt = it.createdAt!!,
        lon = it.lon,
        lat = it.lat
      )
    }

  fun mapResponsePagingStoryToModel(input: PagingData<StoryListResponse>): PagingData<StoryList> =
    input.map {
      StoryList(
        id = it.id,
        name = it.name!!,
        description = it.description!!,
        photoUrl = it.photoUrl!!,
        createdAt = it.createdAt!!,
        lon = it.lon,
        lat = it.lat
      )
    }

  fun mapModelPagingStoryToResponse(input: PagingData<StoryList>): PagingData<StoryListResponse> =
    input.map {
      StoryListResponse(
        id = it.id,
        name = it.name,
        description = it.description,
        photoUrl = it.photoUrl,
        createdAt = it.createdAt,
        lon = it.lon,
        lat = it.lat
      )
    }

  fun mapModelFavoriteStoryToEntity(input: FavoriteStory) =
    FavoriteStoryEntity(
      id = input.id,
      name = input.name,
      description = input.description,
      photoUrl = input.photoUrl,
      createdAt = input.createdAt,
      lon = input.lon,
      lat = input.lat
    )

  fun mapModelFavoriteStoryToResponse(input: FavoriteStory) =
    StoryListResponse(
      id = input.id,
      name = input.name,
      description = input.description,
      photoUrl = input.photoUrl,
      createdAt = input.createdAt,
      lon = input.lon,
      lat = input.lat
    )

  fun mapResponseRegisterToModel(input: RegisterResponse) = Register(
    error = input.error,
    message = input.message
  )

  fun mapResponseLoginToModel(input: LoginResponse) = Login(
    error = input.error,
    message = input.message,
    loginResult = mapResponseLoginResultToModel(input.loginResult)
  )

  private fun mapResponseLoginResultToModel(input: LoginResultResponse) = LoginResult(
    useId = input.useId,
    name = input.name,
    token = input.token
  )

}