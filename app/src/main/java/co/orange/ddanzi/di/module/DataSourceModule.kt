package co.orange.ddanzi.di.module

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dataSource.DeviceDataSource
import co.orange.data.dataSource.HomeDataSource
import co.orange.data.dataSource.ProfileDataSource
import co.orange.data.dataSource.SearchDataSource
import co.orange.data.dataSourceImpl.AuthDataSourceImpl
import co.orange.data.dataSourceImpl.DeviceDataSourceImpl
import co.orange.data.dataSourceImpl.HomeDataSourceImpl
import co.orange.data.dataSourceImpl.ProfileDataSourceImpl
import co.orange.data.dataSourceImpl.SearchDataSourceImpl
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
    fun provideDeviceDataSource(deviceDataSourceImpl: DeviceDataSourceImpl): DeviceDataSource = deviceDataSourceImpl

    @Provides
    @Singleton
    fun provideSearchDataSource(searchDataSourceImpl: SearchDataSourceImpl): SearchDataSource = searchDataSourceImpl

    @Provides
    @Singleton
    fun provideProfileDataSource(profileDataSourceImpl: ProfileDataSourceImpl): ProfileDataSource = profileDataSourceImpl

    @Provides
    @Singleton
    fun providesSettingDataSource(searchDataSourceImpl: SearchDataSourceImpl): SearchDataSource = searchDataSourceImpl
}
