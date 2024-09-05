buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath(ClassPathPlugins.gradle)
        classpath(ClassPathPlugins.kotlinGradle)
        classpath(ClassPathPlugins.hilt)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
