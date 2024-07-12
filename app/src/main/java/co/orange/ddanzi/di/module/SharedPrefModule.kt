package co.orange.ddanzi.di.module

import android.content.Context
import android.content.SharedPreferences
import co.orange.data.local.UserSharedPref
import co.orange.data.local.UserSharedPrefImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPref(sharedPrefImpl: UserSharedPrefImpl): UserSharedPref = sharedPrefImpl
}
