import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import team.aliens.dms.kmp.buildsrc.ProjectProperties
import team.aliens.dms.kmp.buildsrc.Versions

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ktlint)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
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
            baseName = "ui"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.lifecycle.viewmodel.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "team.aliens.dms.kmp.core.ui"
    compileSdk = ProjectProperties.COMPILE_SDK
    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
}