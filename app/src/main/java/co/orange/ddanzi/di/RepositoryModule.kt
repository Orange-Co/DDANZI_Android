package co.orange.ddanzi.di

import co.orange.data.repositoryImpl.AuthRepositoryImpl
import co.orange.data.repositoryImpl.HomeRepositoryImpl
import co.orange.domain.repository.AuthRepository
import co.orange.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository = authRepositoryImpl

    @Provides
    @Singleton
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository = homeRepositoryImpl
}
