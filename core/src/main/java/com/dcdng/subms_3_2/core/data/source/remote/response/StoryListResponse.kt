package com.dcdng.subms_3_2.core.data.source.remote.response

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryListResponse(

  @PrimaryKey
  @field:SerializedName("id")
  val id: String,

  @field:SerializedName("name")
  val name: String? = null,

  @field:SerializedName("description")
  val description: String? = null,

  @field:SerializedName("photoUrl")
  val photoUrl: String? = null,

  @field:SerializedName("createdAt")
  val createdAt: String? = null,

  @field:SerializedName("lon")
  val lon: Double,
//  val lon: Double? = 0.0,

  @field:SerializedName("lat")
  val lat: Double
//  val lat: Double? = 0.0
) : Parcelable
