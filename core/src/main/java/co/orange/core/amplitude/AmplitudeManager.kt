package co.orange.core.amplitude

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

    fun updateProperty(
        propertyName: String,
        value: String,
    ) {
        amplitude.identify(Identify().set(propertyName, value))
    }

    fun updateIntProperty(
        propertyName: String,
        intValue: Int,
    ) {
        amplitude.identify(Identify().set(propertyName, intValue))
    }

    fun plusIntProperty(
        propertyName: String,
        addedValue: Int,
    ) {
        amplitude.identify(Identify().add(propertyName, addedValue))
    }
}
