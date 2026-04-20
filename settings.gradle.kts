rootProject.name = "DmsKmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

include(":core:network")
include(":core:datastore")
include(":core:database")
include(":core:design-system")
include(":core:domain")
include(":core:common")
include(":core:data")
include(":core:model")
include(":core:ui")
include(":core:util")
include(":core:media")
include(":core:notification")

include(":feature:splash")
include(":feature:signin")
include(":feature:signup")
include(":feature:home")
include(":feature:latestudy")
include(":feature:application")
include(":feature:notice")
include(":feature:mypage")
include(":feature:outing")
include(":feature:remain")
include(":feature:vote")
include(":feature:volunteer")
include(":feature:point")
include(":feature:meal")
include(":feature:onboarding")
include(":feature:find-id")
include(":feature:reset-password")
include(":feature:notification")
include(":feature:setting")
include(":feature:editpassword")
include(":feature:profile")
