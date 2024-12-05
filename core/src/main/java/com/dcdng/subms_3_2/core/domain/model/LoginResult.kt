package com.dcdng.subms_3_2.core.domain.model

data class LoginResult(
  val useId: Boolean,
  val name: String,
  val token: String
)
