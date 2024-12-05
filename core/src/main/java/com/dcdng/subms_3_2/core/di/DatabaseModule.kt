package com.dcdng.subms_3_2.core.di

import android.content.Context
import androidx.room.Room
import com.dcdng.subms_3_2.core.data.source.local.room.FavoriteDao
import com.dcdng.subms_3_2.core.data.source.local.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase {
    val passPhrase: ByteArray = SQLiteDatabase.getBytes("subms-3.2".toCharArray())
    val supportFactory = SupportFactory(passPhrase)
    return Room.databaseBuilder(
      context,
      FavoriteDatabase::class.java,
      "FavoriteStory.db"
    )
      .fallbackToDestructiveMigration()
      .openHelperFactory(supportFactory)
      .build()
  }

  @Provides
  fun provideFavoriteDao(database: FavoriteDatabase): FavoriteDao = database.favoriteDao()

}