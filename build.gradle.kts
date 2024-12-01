buildscript {
  val agp_version by extra("8.1.0")
  val agp_version1 by extra("8.5.2")
  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
  }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.5.2" apply false
  id("org.jetbrains.kotlin.android") version "1.8.10" apply false
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
  id("com.android.library") version "8.1.0" apply false
  id("com.android.dynamic-feature") version "8.1.0" apply false
}
val compileSdkVersion by extra(34)
val defaultTargetSdkVersion by extra(34)
