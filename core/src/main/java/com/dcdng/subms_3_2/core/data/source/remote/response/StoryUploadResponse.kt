package com.dcdng.subms_3_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class StoryUploadResponse(
  @field:SerializedName("error")
  var error: Boolean,

  @field:SerializedName("message")
  var message: String
)
