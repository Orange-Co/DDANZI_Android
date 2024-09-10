package co.orange.ddanzi

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import co.orange.ddanzi.BuildConfig.AMPLITUDE_KEY
import co.orange.ddanzi.BuildConfig.NATIVE_APP_KEY
import co.orange.ddanzi.manager.AmplitudeManager
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKakaoSdk()
        initAmplitude()
        setDayMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }

    private fun initAmplitude() {
        AmplitudeManager.init(this, AMPLITUDE_KEY)
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
