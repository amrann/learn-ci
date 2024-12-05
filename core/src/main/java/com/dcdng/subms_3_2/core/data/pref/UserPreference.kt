package com.dcdng.subms_3_2.core.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dcdng.subms_3_2.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

  suspend fun saveSession(user: UserModel) {
    dataStore.edit { preferences ->
      preferences[TOKEN_KEY] = user.token
      preferences[IS_LOGIN_KEY] = true
    }
  }

  fun getSession(): Flow<UserModel> {
    return dataStore.data.map { preferences ->
      UserModel(
        preferences[TOKEN_KEY] ?: "",
        preferences[IS_LOGIN_KEY] ?: false
      )
    }
  }

  suspend fun logout() {
    dataStore.edit { preferences ->
      preferences.clear()
    }
  }

  private companion object {
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
  }
}