package com.dcdng.subms_3_2.core.domain.model


data class Login(
  val error: Boolean,
  val message: String,
  val loginResult: LoginResult
)
