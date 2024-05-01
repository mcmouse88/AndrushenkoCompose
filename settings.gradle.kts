@file:Suppress("UnstableApiUsage")

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
