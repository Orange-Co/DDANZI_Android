package co.orange.ddanzi.di.module

import co.orange.data.service.AuthService
import co.orange.data.service.BuyService
import co.orange.data.service.DeviceService
import co.orange.data.service.HomeService
import co.orange.data.service.IamportService
import co.orange.data.service.InterestService
import co.orange.data.service.ProfileService
import co.orange.data.service.SearchService
import co.orange.data.service.SettingService
import co.orange.ddanzi.di.interceptor.RetrofitQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @RetrofitQualifier.NOTOKEN retrofit: Retrofit,
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideDeviceService(
        @RetrofitQualifier.DEVICE retrofit: Retrofit,
    ): DeviceService = retrofit.create(DeviceService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): SearchService = retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideBuyService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): BuyService = retrofit.create(BuyService::class.java)

    @Provides
    @Singleton
    fun provideProfileService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): ProfileService = retrofit.create(ProfileService::class.java)

    @Provides
    @Singleton
    fun provideSettingService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): SettingService = retrofit.create(SettingService::class.java)

    @Provides
    @Singleton
    fun provideInterestService(
        @RetrofitQualifier.JWT retrofit: Retrofit,
    ): InterestService = retrofit.create(InterestService::class.java)

    @Provides
    @Singleton
    fun provideIamportService(
        @RetrofitQualifier.IAMPORT retrofit: Retrofit,
    ): IamportService = retrofit.create(IamportService::class.java)
}
