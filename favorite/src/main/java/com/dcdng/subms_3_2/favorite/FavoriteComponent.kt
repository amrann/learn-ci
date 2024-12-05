package com.dcdng.subms_3_2.favorite

import android.content.Context
import com.dcdng.subms_3_2.core.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
  fun inject(activity: FavoriteActivity)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
    fun build(): FavoriteComponent
  }
}