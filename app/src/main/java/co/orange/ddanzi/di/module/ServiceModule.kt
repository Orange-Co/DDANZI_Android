package co.orange.ddanzi.di.module

import co.orange.data.service.AuthService
import co.orange.data.service.DetailService
import co.orange.data.service.HomeService
import co.orange.data.service.SearchService
import co.orange.ddanzi.di.qualifier.RetrofitQualifier
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
        @RetrofitQualifier.NOTOKEN retrofit: Retrofit,
    ): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideDetailService(
        @RetrofitQualifier.DEVICE retrofit: Retrofit,
    ): DetailService = retrofit.create(DetailService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(
        @RetrofitQualifier.NOTOKEN retrofit: Retrofit,
    ): SearchService = retrofit.create(SearchService::class.java)
}
