package co.orange.ddanzi.di.module

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dataSource.DetailDataSource
import co.orange.data.dataSource.HomeDataSource
import co.orange.data.dataSourceImpl.AuthDataSourceImpl
import co.orange.data.dataSourceImpl.DetailDataSourceImpl
import co.orange.data.dataSourceImpl.HomeDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource = authDataSourceImpl

    @Provides
    @Singleton
    fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource = homeDataSourceImpl

    @Provides
    @Singleton
    fun provideDetailDataSource(detailDataSourceImpl: DetailDataSourceImpl): DetailDataSource = detailDataSourceImpl
}
