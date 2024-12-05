package com.dcdng.subms_3_2.core.utils

sealed class Result<out R> private constructor() {
  data class Success<out T>(val data: T) : Result<T>()
//  data class Success<out T>(val data: T) : Result<Nothing>()
  data class Error(val error: String) : Result<Nothing>()
  object Loading : Result<Nothing>()
}
