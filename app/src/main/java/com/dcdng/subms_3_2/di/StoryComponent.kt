package com.dcdng.subms_3_2.di

import android.content.Context
import com.dcdng.subms_3_2.MainActivity
import com.dcdng.subms_3_2.MapsActivity
import com.dcdng.subms_3_2.core.di.StoryModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [StoryModuleDependencies::class])
interface StoryComponent {
  fun inject(activity: MainActivity)
  fun inject(activity: MapsActivity)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun appDependencies(storyModuleDependencies: StoryModuleDependencies): Builder
    fun build(): StoryComponent
  }
}