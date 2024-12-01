package com.dcdng.subms_3_2.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_favorite")
data class FavoriteStoryEntity(

  @PrimaryKey
  @ColumnInfo(name = "id")
  val id: String,

  @ColumnInfo(name = "name")
//  val name: String? = null,
  val name: String,

  @ColumnInfo(name = "description")
  val description: String,

  @ColumnInfo(name = "photoUrl")
  val photoUrl: String,

  @ColumnInfo(name = "createdAt")
  val createdAt: String,

  @ColumnInfo(name = "lon")
  val lon: Double,

  @ColumnInfo(name = "lat")
  val lat: Double
) : Parcelable