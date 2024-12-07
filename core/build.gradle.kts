plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
  id("kotlin-kapt")
}

android {
  namespace = "com.dcdng.subms_3_2.core"
  compileSdk = 34

  defaultConfig {
    minSdk = 21

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
//    debug {
//      isMinifyEnabled = true
//      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//    }
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
}

dependencies {
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  implementation("androidx.paging:paging-runtime-ktx:3.1.0")

  implementation("com.github.bumptech.glide:glide:4.16.0")

  implementation ("io.reactivex.rxjava2:rxjava:2.2.19")
  implementation ("com.jakewharton.rxbinding2:rxbinding:2.0.0")

  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
  implementation("androidx.activity:activity-ktx:1.7.2")

  implementation("androidx.datastore:datastore-preferences:1.0.0")
//  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
//  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
//  implementation("androidx.activity:activity-ktx:1.7.2")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

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
  implementation("androidx.room:room-ktx:2.5.2")

  //sqlchiper
  implementation("net.zetetic:android-database-sqlcipher:4.5.4")
//  implementation("androidx.sqlite:sqlite-ktx:2.4.0") // for API 34
  implementation("androidx.sqlite:sqlite:2.2.0")
}