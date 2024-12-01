package com.dcdng.subms_3_2.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoriesResponse(
  @field:SerializedName("error")
  val error: Boolean,

  @field:SerializedName("message")
  val message: String? = null,

  @field:SerializedName("listStory")
//  val listStory: List<StoryListResponse> = emptyList()
  val listStory: List<StoryListResponse>
) : Parcelable
