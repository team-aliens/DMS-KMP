# Gradle 컨벤션 플러그인 도입 가이드

## 목표
Gradle 빌드 스크립트(`build.gradle.kts`)에 반복적으로 나타나는 설정을 '컨벤션 플러그인'으로 추출하여 중복을 제거하고 유지보수성을 향상시킵니다.

## 단계별 실행 계획

### 1단계: 컨벤션 플러그인 파일 생성
1.  `buildSrc/src/main/kotlin/` 디렉토리로 이동합니다.
2.  `dms-kmp-library.gradle.kts` 이름으로 새 파일을 생성합니다.

### 2단계: 공통 빌드 로직 이전
`dms-kmp-library.gradle.kts` 파일에 KMP 라이브러리 모듈의 공통 설정을 아래와 같이 작성합니다.

```kotlin
// dms-kmp-library.gradle.kts

@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import team.aliens.dms.kmp.buildsrc.ProjectProperties
import team.aliens.dms.kmp.buildsrc.Versions

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
    namespace = "team.aliens.dms.kmp.core.${project.name}"
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

**주요 변경 사항 설명:**
- `baseName = project.name`: iOS 프레임워크의 이름을 모듈의 이름으로 동적으로 설정합니다.
- `namespace = "team.aliens.dms.kmp.core.${project.name}"`: Android 네임스페이스를 모듈 이름에 따라 동적으로 설정합니다.

### 3단계: 기존 모듈에 컨벤션 플러그인 적용
`core` 디렉토리 아래의 각 라이브러리 모듈 (`data`, `domain`, `model` 등)의 `build.gradle.kts` 파일을 다음과 같이 수정합니다.

**수정 후 예시 (`core/data/build.gradle.kts`):**
```kotlin
plugins {
    id("dms-kmp-library")
    alias(libs.plugins.ktlint)
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

// android { ... } 블록과 기존 kotlin { ... } 블록의 타겟 설정은 모두 삭제합니다.
```

**수정 대상 파일 목록:**
- `core/common/build.gradle.kts`
- `core/data/build.gradle.kts`
- `core/database/build.gradle.kts`
- `core/datastore/build.gradle.kts`
- `core/design-system/build.gradle.kts`
- `core/domain/build.gradle.kts`
- `core/media/build.gradle.kts`
- `core/model/build.gradle.kts`
- `core/network/build.gradle.kts`
- `core/ui/build.gradle.kts`
- `core/util/build.gradle.kts`
- (`feature` 모듈 등 다른 모든 라이브러리 모듈에 동일하게 적용)

### 4단계: 빌드 검증
1.  터미널에서 Gradle 프로젝트를 동기화(sync)합니다.
2.  아래의 명령어를 실행하여 프로젝트 전체가 성공적으로 빌드되는지 확인합니다.
    ```shell
    ./gradlew build
    ```
3.  오류가 발생할 경우, 2단계와 3단계의 수정 사항이 올바르게 적용되었는지 다시 확인합니다.
