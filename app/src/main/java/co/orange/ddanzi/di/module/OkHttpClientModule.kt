package co.orange.ddanzi.di.module

import co.orange.ddanzi.di.interceptor.RetrofitQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    @Singleton
    @RetrofitQualifier.NOTOKEN
    fun provideReissueOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @RetrofitQualifier.JWT
    fun provideJWTOkHttpClient(
        loggingInterceptor: Interceptor,
        @RetrofitQualifier.JWT authInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    @RetrofitQualifier.DEVICE
    fun provideDeviceOkHttpClient(
        loggingInterceptor: Interceptor,
        @RetrofitQualifier.DEVICE deviceTokenInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(deviceTokenInterceptor)
            .build()
}
