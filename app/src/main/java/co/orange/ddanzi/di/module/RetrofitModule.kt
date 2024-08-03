package co.orange.ddanzi.di.module

import co.orange.ddanzi.BuildConfig.BASE_URL
import co.orange.ddanzi.di.interceptor.RetrofitQualifier
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(
            APPLICATION_JSON.toMediaType(),
        )

    private fun createRetrofit(
        client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    @RetrofitQualifier.NOTOKEN
    fun provideReissueRetrofit(
        @RetrofitQualifier.NOTOKEN client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = createRetrofit(client, factory)

    @Provides
    @Singleton
    @RetrofitQualifier.JWT
    fun provideJWTRetrofit(
        @RetrofitQualifier.JWT client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = createRetrofit(client, factory)

    @Provides
    @Singleton
    @RetrofitQualifier.DEVICE
    fun provideDeviceRetrofit(
        @RetrofitQualifier.DEVICE client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = createRetrofit(client, factory)
}
