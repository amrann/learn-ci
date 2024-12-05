package com.dcdng.subms_3_2.core.data.source.remote

interface TokenProvider {
  fun getToken(): String?
  fun updateToken(token: String)
}