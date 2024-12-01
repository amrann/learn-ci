package com.dcdng.subms_3_2.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dcdng.subms_3_2.core.data.source.local.entity.FavoriteStoryEntity

@Database(entities = [FavoriteStoryEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

  abstract fun favoriteDao(): FavoriteDao
}