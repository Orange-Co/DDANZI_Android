import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Versions.kotlinVersion
}

android {
    namespace = "co.orange.data"
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "IAMPORT_API_KEY",
            gradleLocalProperties(rootDir).getProperty("iamport.api.key"),
        )

        buildConfigField(
            "String",
            "IAMPORT_API_SECRET",
            gradleLocalProperties(rootDir).getProperty("iamport.api.secret"),
        )
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    AndroidXDependencies.run {
        implementation(hilt)
        implementation(security)
        implementation(coreKtx)
    }

    KotlinDependencies.run {
        implementation(kotlin)
        implementation(jsonSerialization)
        implementation(coroutines)
        implementation(dateTime)
    }

    ThirdPartyDependencies.run {
        implementation(retrofit)
        implementation(okHttp)
        implementation(okHttpBom)
        implementation(okHttpLoggingInterceptor)
        implementation(retrofitJsonConverter)
        implementation(timber)
    }

    TestDependencies.run {
        testImplementation(jUnit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
    }
}
