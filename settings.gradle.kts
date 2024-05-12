@file:Suppress("UnstableApiUsage")

include(":back-handler")


include(":navigation-bar")


include(":top-app-bar")


include(":simple-items-list")


include(":constraint-layout")


include(":composition-local-refresh")


include(":composition-local-sample")


include(":user_list")


pluginManagement {
    repositories {
        google()
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

rootProject.name = "AndrushchenkoJetpackComposeCourse"
include(":recycler-view-example")
include(":simple-counter")
