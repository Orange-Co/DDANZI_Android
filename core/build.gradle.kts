plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "co.orange.core"
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    KotlinDependencies.run {
        implementation(kotlin)
    }

    AndroidXDependencies.run {
        implementation(lifeCycleKtx)
        implementation(hilt)
    }

    GoogleDependencies.run {
        implementation(materialDesign)
    }

    KaptDependencies.run {
        kapt(hiltAndroidCompiler)
    }

    ThirdPartyDependencies.run {
        implementation(timber)
        implementation(amplitude)
    }

    TestDependencies.run {
        testImplementation(jUnit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
    }
}
