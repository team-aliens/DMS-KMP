import team.aliens.dms.kmp.buildsrc.ProjectProperties
import team.aliens.dms.kmp.buildsrc.Versions

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Versions.java.toString()
            }
        }
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "database"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.android.driver)
        }
        commonMain.dependencies {
            implementation(libs.coroutines.extensions)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.driver)
        }
    }

    sqldelight {
        databases {
            create("DmsDatabase") {
                packageName = "team.aliens.dms.kmp.core.database"
            }
        }
    }
}

android {
    namespace = "team.aliens.dms.kmp.core.database"
    compileSdk = ProjectProperties.COMPILE_SDK
    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
}
