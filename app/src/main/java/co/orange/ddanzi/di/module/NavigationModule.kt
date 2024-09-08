package co.orange.ddanzi.di.module

import android.content.Context
import co.orange.core.navigation.NavigationManager
import co.orange.ddanzi.di.navigate.NavigationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationManager(
        @ApplicationContext context: Context,
    ): NavigationManager = NavigationManagerImpl(context)
}
