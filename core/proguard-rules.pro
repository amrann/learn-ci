-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.dcdng.subms_3_2.core.adapter.ListFavoriteStoriesAdapter { *; }

-keep class com.dcdng.subms_3_2.core.adapter.ListStoriesAdapter { *; }
-keep class com.dcdng.subms_3_2.core.adapter.ListStoriesAdapter$OnItemClickListener { *; }

-keep class com.dcdng.subms_3_2.core.adapter.LoadingStateAdapter { *; }

-keep class com.dcdng.subms_3_2.core.data.pref.UserPreference { *; }

-keep class com.dcdng.subms_3_2.core.data.source.local.LocalDataSource { *; }

-keep class com.dcdng.subms_3_2.core.data.source.remote.RemoteDataSource { *; }
-keep class com.dcdng.subms_3_2.core.data.source.remote.TokenProvider { *; }

-keep class com.dcdng.subms_3_2.core.data.source.remote.network.ApiConfig { *; }
-keep class com.dcdng.subms_3_2.core.data.source.remote.network.ApiService { *; }

-keep class com.dcdng.subms_3_2.core.data.source.remote.response.** { *; }

-keep class com.dcdng.subms_3_2.core.di.NetworkModule { *; }
-keep class com.dcdng.subms_3_2.core.di.NetworkModule_* { *; }

-keep class com.dcdng.subms_3_2.core.di.DataStoreModule { *; }
-keep class com.dcdng.subms_3_2.core.di.DataStoreModule_* { *; }

-keep class com.dcdng.subms_3_2.core.di.DatabaseModule { *; }
-keep class com.dcdng.subms_3_2.core.di.DatabaseModule_* { *; }

-keep class com.dcdng.subms_3_2.core.di.FavoriteModuleDependencies { *; }

-keep class com.dcdng.subms_3_2.core.di.RepositoryModule { *; }
-keep class com.dcdng.subms_3_2.core.di.RepositoryModule_* { *; }

-keep class com.dcdng.subms_3_2.core.di.StoryModuleDependencies { *; }

-keep class com.dcdng.subms_3_2.core.di.TokenModule { *; }
-keep class com.dcdng.subms_3_2.core.di.TokenModule_* { *; }

-keep class com.dcdng.subms_3_2.core.domain.irepository.** { *; }

-keep class com.dcdng.subms_3_2.core.domain.model.** { *; }

-keep class com.dcdng.subms_3_2.core.domain.usecase.** { *; }

-keep class com.dcdng.subms_3_2.core.utils.Result { *; }
-keep class com.dcdng.subms_3_2.core.utils.Result$* { *; }

-keep class com.dcdng.subms_3_2.core.utils.AppExecutors { *; }
-keep class com.dcdng.subms_3_2.core.utils.CustomButton { *; }
-keep class com.dcdng.subms_3_2.core.utils.DataMapper { *; }
-keep class com.dcdng.subms_3_2.core.utils.EmailEditText { *; }
-keep class com.dcdng.subms_3_2.core.utils.PasswordEditText { *; }
-keep class com.dcdng.subms_3_2.core.utils.UtilsKt { *; }