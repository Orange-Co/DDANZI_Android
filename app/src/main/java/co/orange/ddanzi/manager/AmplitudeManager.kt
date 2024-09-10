package co.orange.ddanzi.manager

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.events.Identify

object AmplitudeManager {
    private lateinit var amplitude: Amplitude

    fun init(
        context: Context,
        apiKey: String,
    ) {
        amplitude =
            Amplitude(
                Configuration(
                    apiKey = apiKey,
                    context = context.applicationContext,
                ),
            )
    }

    fun trackEvent(
        eventName: String,
        properties: Map<String, Any>? = null,
    ) {
        if (properties == null) {
            amplitude.track(eventName)
        } else {
            amplitude.track(eventName, properties)
        }
    }

    fun updateProperties(
        propertyName: String,
        values: String,
    ) {
        amplitude.identify(Identify().set(propertyName, values))
    }

    fun updateIntProperties(
        propertyName: String,
        intValues: Int,
    ) {
        amplitude.identify(Identify().set(propertyName, intValues))
    }

    fun plusIntProperties(propertyName: String) {
        amplitude.identify(Identify().add(propertyName, 1))
    }
}
