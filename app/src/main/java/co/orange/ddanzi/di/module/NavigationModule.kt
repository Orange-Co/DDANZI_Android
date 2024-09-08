package co.orange.ddanzi.di.module

import co.orange.core.navigation.NavigationManager
import co.orange.ddanzi.di.navigate.NavigationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationManager(navigationManagerImpl: NavigationManagerImpl): NavigationManager = navigationManagerImpl
}
