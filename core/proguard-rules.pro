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

# Dontwarn StringConcatFactory
-dontwarn java.lang.invoke.StringConcatFactory

## Keep UserPreference class
#-keep class com.dcdng.subms_3_2.core.data.pref.UserPreference.** { *; }
#
#-keep class com.dcdng.subms_3_2.core.data.source.local.LocalDataSource.** { *; }
#-keep class com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource.** { *; }
#
## Preserve Hilt components
#-keep class com.dcdng.subms_3_2.DaggerMyApplication_HiltComponents_SingletonC$SingletonCImpl$SwitchingProvider.** { *; }
