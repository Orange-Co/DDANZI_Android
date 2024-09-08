package co.orange.ddanzi.di.navigate

import android.content.Context
import android.content.Intent
import co.orange.core.navigation.NavigationManager
import javax.inject.Inject

class NavigationManagerImpl
    @Inject
    constructor(
        private val context: Context,
    ) : NavigationManager {
        override fun navigateToFeatureA() {
            val intent = Intent(context, FeatureAActivity::class.java)
            context.startActivity(intent)
        }

        override fun navigateToFeatureB() {
            val intent = Intent(context, FeatureBActivity::class.java)
            context.startActivity(intent)
        }
    }
