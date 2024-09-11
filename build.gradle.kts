buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        ClassPathPlugins.run {
            classpath(gradle)
            classpath(kotlinGradle)
            classpath(hilt)
            classpath(googleServices)
            classpath(crashlyticsGradle)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply {
    from("gradle/projectDependencyGraph.gradle")
}
