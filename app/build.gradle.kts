plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.dcdng.subms_3_2"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.dcdng.subms_3_2"
    minSdk = 21
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
    debug {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }

  dynamicFeatures += setOf(":favorite")
}

dependencies {
  implementation(project(":core"))

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("com.google.android.material:material:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("com.google.android.gms:play-services-maps:18.1.0")
  implementation("androidx.navigation:navigation-fragment-ktx:2.8.2")
  implementation("androidx.navigation:navigation-ui-ktx:2.8.2")
  implementation("androidx.activity:activity:1.9.3")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  implementation("androidx.datastore:datastore-preferences:1.0.0")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
  implementation("androidx.activity:activity-ktx:1.7.2")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

  implementation("com.github.bumptech.glide:glide:4.16.0")

  implementation("androidx.exifinterface:exifinterface:1.3.6")

  implementation("androidx.paging:paging-runtime-ktx:3.1.0")

//  //mockito
//  testImplementation("org.mockito:mockito-core:4.4.0")
//  testImplementation("org.mockito:mockito-inline:4.4.0")

//  androidTestImplementation("androidx.arch.core:core-testing:2.1.0") //InstantTaskExecutorRule
//  androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") //TestDispatcher
//  testImplementation("androidx.arch.core:core-testing:2.1.0") // InstantTaskExecutorRule
//  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") //TestDispatcher

  implementation ("io.reactivex.rxjava2:rxjava:2.2.19")
  implementation ("com.jakewharton.rxbinding2:rxbinding:2.0.0")

  // dagger
  implementation ("com.google.dagger:hilt-android:2.45")
  kapt ("com.google.dagger:hilt-android-compiler:2.45")

  // room
  implementation("androidx.room:room-common:2.5.2")
  implementation("androidx.room:room-runtime:2.5.2")
  kapt ("androidx.room:room-compiler:2.5.2")

  // coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
  implementation("androidx.room:room-ktx:2.5.2")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") //untuk lifecycleScope

}