enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Onu"
include(":app")

include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:designsystem")
include(":core:navigation")

include(":feature:moments:impl")
include(":feature:moments:api")
include(":feature:messenger:impl")
include(":feature:messenger:api")
include(":feature:foryou:api")
include(":feature:foryou:impl")


check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    Now in Android requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}



include(":core:common")
include(":core:ui")
include(":core:database")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:friends:api")
include(":feature:friends:impl")
include(":feature:profile:impl")
include(":feature:profile:api")
include(":feature:notification:impl")
include(":feature:notification:api")
