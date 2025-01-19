plugins {
  id("com.android.dynamic-feature")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
  id("com.google.dagger.hilt.android")
  id("com.google.devtools.ksp")
}
android {
  namespace = "com.dcdng.subms_3_2.favorite"
  compileSdk = 34

  defaultConfig {
    minSdk = 21
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures {
    viewBinding = true
  }

}

dependencies {
  implementation(project(":app"))
  implementation(project(":core"))

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

  // dagger
  implementation ("com.google.dagger:hilt-android:2.52")
  ksp ("com.google.dagger:hilt-android-compiler:2.52")
//  implementation ("com.google.dagger:dagger:2.45")
//  kapt ("com.google.dagger:dagger-compiler:2.45")

  // room
  implementation("androidx.room:room-common:2.6.1")
  implementation("androidx.room:room-runtime:2.6.1")
  ksp ("androidx.room:room-compiler:2.6.1")

  // coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
  implementation("androidx.room:room-ktx:2.6.1")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

}