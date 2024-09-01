package co.orange.ddanzi.di.module

import co.orange.data.repositoryImpl.AuthRepositoryImpl
import co.orange.data.repositoryImpl.BuyRepositoryImpl
import co.orange.data.repositoryImpl.DeviceRepositoryImpl
import co.orange.data.repositoryImpl.HomeRepositoryImpl
import co.orange.data.repositoryImpl.IamportRepositoryImpl
import co.orange.data.repositoryImpl.InterestRepositoryImpl
import co.orange.data.repositoryImpl.ProfileRepositoryImpl
import co.orange.data.repositoryImpl.SearchRepositoryImpl
import co.orange.data.repositoryImpl.SellRepositoryImpl
import co.orange.data.repositoryImpl.SettingRepositoryImpl
import co.orange.data.repositoryImpl.UploadRepositoryImpl
import co.orange.domain.repository.AuthRepository
import co.orange.domain.repository.BuyRepository
import co.orange.domain.repository.DeviceRepository
import co.orange.domain.repository.HomeRepository
import co.orange.domain.repository.IamportRepository
import co.orange.domain.repository.InterestRepository
import co.orange.domain.repository.ProfileRepository
import co.orange.domain.repository.SearchRepository
import co.orange.domain.repository.SellRepository
import co.orange.domain.repository.SettingRepository
import co.orange.domain.repository.UploadRepository
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

    @Provides
    @Singleton
    fun provideSellRepository(sellRepositoryImpl: SellRepositoryImpl): SellRepository = sellRepositoryImpl

    @Provides
    @Singleton
    fun provideBuyRepository(buyRepositoryImpl: BuyRepositoryImpl): BuyRepository = buyRepositoryImpl

    @Provides
    @Singleton
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository = profileRepositoryImpl

    @Provides
    @Singleton
    fun provideSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository = settingRepositoryImpl

    @Provides
    @Singleton
    fun provideInterestRepository(interestRepositoryImpl: InterestRepositoryImpl): InterestRepository = interestRepositoryImpl

    @Provides
    @Singleton
    fun provideUploadRepository(uploadRepositoryImpl: UploadRepositoryImpl): UploadRepository = uploadRepositoryImpl

    @Provides
    @Singleton
    fun provideIamportRepository(iamportRepositoryImpl: IamportRepositoryImpl): IamportRepository = iamportRepositoryImpl
}
