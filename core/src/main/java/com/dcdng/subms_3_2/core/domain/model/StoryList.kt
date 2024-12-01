package com.dcdng.subms_3_2.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryList(
  val id: String,
  val name: String,
  val description: String,
  val photoUrl: String,
  val createdAt: String,
  val lon: Double,
  val lat: Double
) : Parcelable
