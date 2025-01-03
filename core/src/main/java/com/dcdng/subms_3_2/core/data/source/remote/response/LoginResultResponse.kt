package com.dcdng.subms_3_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResultResponse(
  @field:SerializedName("userId")
  val useId: Boolean,

  @field:SerializedName("name")
  val name: String,

  @field:SerializedName("token")
  val token: String
)
