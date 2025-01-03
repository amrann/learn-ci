plugins {
  id("com.android.dynamic-feature")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}
android {
  namespace = "com.dcdng.subms_3_2.favorite"
  compileSdk = 34

  defaultConfig {
    minSdk = 21
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":app"))
  implementation(project(":core"))

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  // dagger
  implementation ("com.google.dagger:hilt-android:2.45")
  kapt ("com.google.dagger:hilt-android-compiler:2.45")
//  implementation ("com.google.dagger:dagger:2.45")
//  kapt ("com.google.dagger:dagger-compiler:2.45")

  // room
  implementation("androidx.room:room-common:2.5.2")
  implementation("androidx.room:room-runtime:2.5.2")
  kapt ("androidx.room:room-compiler:2.5.2")

  // coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
  implementation("androidx.room:room-ktx:2.5.2")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

}