package co.orange.ddanzi.di.module

import co.orange.data.repositoryImpl.AuthRepositoryImpl
import co.orange.data.repositoryImpl.DeviceRepositoryImpl
import co.orange.data.repositoryImpl.HomeRepositoryImpl
import co.orange.data.repositoryImpl.SearchRepositoryImpl
import co.orange.domain.repository.AuthRepository
import co.orange.domain.repository.DeviceRepository
import co.orange.domain.repository.HomeRepository
import co.orange.domain.repository.SearchRepository
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

    @Provides
    @Singleton
    fun provideDeviceRepository(deviceRepositoryImpl: DeviceRepositoryImpl): DeviceRepository = deviceRepositoryImpl

    @Provides
    @Singleton
    fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository = searchRepositoryImpl
}
