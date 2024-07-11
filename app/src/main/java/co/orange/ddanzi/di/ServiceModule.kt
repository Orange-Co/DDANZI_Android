package co.orange.ddanzi.di

import co.orange.data.service.AuthService
import co.orange.data.service.HomeService
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

    // TODO 추후 수정
    @Provides
    @Singleton
    fun provideHomeService(
        @RetrofitQualifier.NOTOKEN retrofit: Retrofit,
    ): HomeService = retrofit.create(HomeService::class.java)
}
