# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep UserPreference class
-keep class com.dcdng.subms_3_2.core.data.pref.UserPreference.** { *; }

-keep class com.dcdng.subms_3_2.core.data.source.local.LocalDataSource.** { *; }
-keep class com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource.** { *; }

# Hilt-specific rules
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keepattributes *Annotation*
-keepattributes Signature

# Preserve Hilt components
-keep class com.dcdng.subms_3_2.DaggerMyApplication_HiltComponents_SingletonC$SingletonCImpl$SwitchingProvider.** { *; }

# Basic Android configuration
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

# Retrofit
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# RxJava
-keep class rx.** { *; }
-dontwarn rx.**

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.migration.**
