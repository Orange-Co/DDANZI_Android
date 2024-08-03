package co.orange.ddanzi.di.module

import co.orange.core.extension.isJsonArray
import co.orange.core.extension.isJsonObject
import co.orange.ddanzi.di.interceptor.AuthInterceptor
import co.orange.ddanzi.di.interceptor.DeviceTokenInterceptor
import co.orange.ddanzi.di.interceptor.RetrofitQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Timber.tag("okhttp").d(JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Timber.tag("okhttp").d(JSONArray(message).toString(4))

                else -> {
                    Timber.tag("okhttp").d("CONNECTION INFO -> $message")
                }
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @RetrofitQualifier.JWT
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    @RetrofitQualifier.DEVICE
    fun provideDeviceTokenInterceptor(deviceTokenInterceptor: DeviceTokenInterceptor): Interceptor = deviceTokenInterceptor
}
