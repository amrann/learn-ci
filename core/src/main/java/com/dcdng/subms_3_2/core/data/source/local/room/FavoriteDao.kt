package com.dcdng.subms_3_2.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dcdng.subms_3_2.core.data.source.local.entity.FavoriteStoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM tb_favorite")
  fun getFavoriteStories(): Flow<List<FavoriteStoryEntity>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertFavoriteStory(favoriteStory: FavoriteStoryEntity)

  @Delete
  fun deleteFavoriteStory(favoriteStory: FavoriteStoryEntity)
}