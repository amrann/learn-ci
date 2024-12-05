package com.dcdng.subms_3_2.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
  var token: String,
  var isLogin: Boolean = false
) : Parcelable