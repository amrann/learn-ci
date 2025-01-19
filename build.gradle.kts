buildscript {
  val agpVersion by extra("8.5.2")
  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
  }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.5.2" apply false
  id("org.jetbrains.kotlin.android") version "2.0.20" apply false
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
  id("com.android.library") version "8.5.2" apply false
  id("com.android.dynamic-feature") version "8.5.2" apply false
  id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
}
