package com.dcdng.subms_3_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
  @field:SerializedName("error")
  val error: Boolean,
  @field:SerializedName("message")
  val message: String
)
