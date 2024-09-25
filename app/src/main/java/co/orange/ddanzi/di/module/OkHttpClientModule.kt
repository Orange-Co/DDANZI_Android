package co.orange.ddanzi.di.module

import co.orange.ddanzi.di.interceptor.RetrofitQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    @Singleton
    @RetrofitQualifier.NOTOKEN
    fun provideNoTokenOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
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
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    @RetrofitQualifier.DEVICE
    fun provideDeviceOkHttpClient(
        loggingInterceptor: Interceptor,
        @RetrofitQualifier.JWT authInterceptor: Interceptor,
        @RetrofitQualifier.DEVICE deviceTokenInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(deviceTokenInterceptor)
            .build()

    @Provides
    @Singleton
    @RetrofitQualifier.IAMPORT
    fun provideIamportOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
}
