pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "DDANZI"

include(":app")
include(":core")
include(":domain")
include(":data")
include(":feature:buy")
include(":feature:sell")
include(":feature:main")
include(":feature:auth")
include(":feature:setting")
