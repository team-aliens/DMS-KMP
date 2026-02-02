# Gradle 컨벤션 플러그인 도입 가이드

## 목표
Gradle 빌드 스크립트(`build.gradle.kts`)에 반복적으로 나타나는 설정을 '컨벤션 플러그인'으로 추출하여 중복을 제거하고 유지보수성을 향상시킵니다.

---

## 사전 준비: buildSrc에서 Version Catalog 접근 설정

컨벤션 플러그인에서 `libs.plugins.xxx`를 사용하려면 `buildSrc/build.gradle.kts`에 플러그인 의존성을 추가해야 합니다.

```kotlin
// buildSrc/build.gradle.kts
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // 컨벤션 플러그인에서 사용할 Gradle 플러그인 의존성
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.android.tools.build:gradle:8.6.0")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.9.3")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.2.20")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.2.20")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.1")
}
```

> **참고**: 버전은 `gradle/libs.versions.toml`의 값과 동기화해야 합니다.

---

## 컨벤션 플러그인 유형

프로젝트에서 사용할 3가지 컨벤션 플러그인:

| 플러그인 | 대상 모듈 | 포함 내용 |
|----------|-----------|-----------|
| `dms-kmp-library` | core/data, core/domain, core/model 등 | KMP 기본 설정 |
| `dms-kmp-compose-library` | core/design-system, core/ui | KMP + Compose |
| `dms-kmp-feature` | feature/* 모듈 | KMP + Compose + Navigation + Koin |

---

## 1단계: 기본 KMP 라이브러리 플러그인 생성

### 파일: `buildSrc/src/main/kotlin/dms-kmp-library.gradle.kts`

```kotlin
// dms-kmp-library.gradle.kts

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import team.aliens.dms.kmp.buildsrc.ProjectProperties
import team.aliens.dms.kmp.buildsrc.Versions

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
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
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = project.name
            isStatic = true
        }
    }
}

android {
    // 모듈 경로에 따라 네임스페이스 동적 생성
    // :core:data -> team.aliens.dms.kmp.core.data
    // :feature:signin -> team.aliens.dms.kmp.feature.signin
    val modulePath = project.path.removePrefix(":").replace(":", ".")
    namespace = "team.aliens.dms.kmp.$modulePath"

    compileSdk = ProjectProperties.COMPILE_SDK
    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
}
```

**주요 특징:**
- `baseName = project.name`: iOS 프레임워크 이름을 모듈명으로 자동 설정
- `namespace` 동적 생성: core/feature 모듈 모두 지원
- ktlint 플러그인 기본 포함

---

## 2단계: Compose UI 라이브러리 플러그인 생성

### 파일: `buildSrc/src/main/kotlin/dms-kmp-compose-library.gradle.kts`

```kotlin
// dms-kmp-compose-library.gradle.kts

plugins {
    id("dms-kmp-library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
    }
}
```

**대상 모듈:**
- `core/design-system`
- `core/ui`

---

## 3단계: Feature 모듈 플러그인 생성

### 파일: `buildSrc/src/main/kotlin/dms-kmp-feature.gradle.kts`

```kotlin
// dms-kmp-feature.gradle.kts

plugins {
    id("dms-kmp-compose-library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

// Version Catalog 접근을 위한 확장
val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Navigation
            implementation(libs.navigation.compose)

            // Koin DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
    }
}
```

**대상 모듈:**
- 모든 `feature/*` 모듈

---

## 4단계: 기존 모듈에 플러그인 적용

### Core 모듈 예시 (`core/data/build.gradle.kts`)

**수정 전:**
```kotlin
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
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "data"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies { ... }
    }
}

android {
    namespace = "team.aliens.dms.kmp.core.data"
    compileSdk = ProjectProperties.COMPILE_SDK
    ...
}
```

**수정 후:**
```kotlin
plugins {
    id("dms-kmp-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(projects.core.network)
            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(projects.core.util)
            implementation(projects.core.media)

            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
```

### Feature 모듈 예시 (`feature/signin/build.gradle.kts`)

**수정 후:**
```kotlin
plugins {
    id("dms-kmp-feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.designSystem)
            implementation(projects.core.model)
            implementation(projects.core.domain)
            implementation(projects.core.ui)
        }
    }
}
```

---

## 5단계: 특수 모듈 처리

일부 모듈은 추가 설정이 필요합니다.

### core/network (BuildConfig 사용)

```kotlin
plugins {
    id("dms-kmp-library")
}

android {
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            buildConfigField("String", "DEV_BASE_URL", "\"https://dev.example.com\"")
        }
        release {
            buildConfigField("String", "PROD_BASE_URL", "\"https://api.example.com\"")
        }
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
    }
}
```

### core/database (SQLDelight 사용)

```kotlin
plugins {
    id("dms-kmp-library")
    alias(libs.plugins.sqlDelight)
}

sqldelight {
    databases {
        create("DmsDatabase") {
            packageName.set("team.aliens.dms.kmp.core.database")
        }
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.driver)
        }
    }
}
```

### core/design-system (Compose 리소스)

```kotlin
plugins {
    id("dms-kmp-compose-library")
}

compose.resources {
    publicResClass = true
    generateResClass = always
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            // 추가 디자인 시스템 의존성
        }
    }
}
```

---

## 수정 대상 파일 목록

### Core 모듈 (dms-kmp-library 적용)
- `core/common/build.gradle.kts`
- `core/data/build.gradle.kts`
- `core/database/build.gradle.kts` (+ SQLDelight)
- `core/datastore/build.gradle.kts`
- `core/domain/build.gradle.kts`
- `core/media/build.gradle.kts`
- `core/model/build.gradle.kts`
- `core/network/build.gradle.kts` (+ BuildConfig)
- `core/util/build.gradle.kts`

### Core UI 모듈 (dms-kmp-compose-library 적용)
- `core/design-system/build.gradle.kts`
- `core/ui/build.gradle.kts`

### Feature 모듈 (dms-kmp-feature 적용)
- 모든 `feature/*/build.gradle.kts`

---

## 빌드 검증

```bash
# Gradle 동기화 후 전체 빌드
./gradlew build

# 특정 모듈 테스트
./gradlew :core:data:compileKotlinMetadata
./gradlew :feature:signin:compileDebugKotlinAndroid

# iOS 빌드 테스트
./gradlew :feature:signin:compileKotlinIosArm64
```

---

## 주의사항

1. **플러그인 적용 순서**: `dms-kmp-library` → `dms-kmp-compose-library` → `dms-kmp-feature` 순서로 상속됨
2. **버전 동기화**: `buildSrc/build.gradle.kts`의 플러그인 버전과 `libs.versions.toml` 버전을 일치시켜야 함
3. **모듈별 의존성**: 각 모듈의 특정 의존성은 해당 `build.gradle.kts`에서 추가
4. **alias() vs id()**: 컨벤션 플러그인 내에서는 `id()` 사용, 모듈에서 외부 플러그인 추가 시 `alias()` 사용