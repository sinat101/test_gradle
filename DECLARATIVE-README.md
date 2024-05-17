# Declarative Gradle DSL

## Current status

This branch has been modified to work with the prototype of the Declarative Gradle DSL. This build relies on nightly versions of Gradle, Android Studio and [Declarative Gradle prototype plugins](https://github.com/gradle/declarative-gradle).

The NowInAndroid project is a "hybrid" build now where there's a mix of declarative and non-declarative (Kotlin DSL) build files. The build can be imported and builds.

The [settings file](settings.gradle.dcl) applies a new "Android ecosystem plugin", which exposes `androidLibrary` and `androidApplication` software types that can be used in subprojects. The current prototype is limited to a single `androidLibrary` software type convention, so only a few subprojects have been converted.

Converted subprojects:
- [`:core:common`](core/common/build.gradle.dcl)
- [`:core:data`](core/data/build.gradle.dcl)
- [`:core:domain`](core/domain/build.gradle.dcl)

The `androidLibrary` software type exposes [several configuration options](https://github.com/gradle/declarative-gradle/blob/main/unified-prototype/unified-plugin/plugin-android/src/main/java/org/gradle/api/experimental/android/library/AndroidLibrary.java) and dependencies. Test related configuration mimics the existing Android extension for now. 

Syntax highlighting is limited to the latest nightly for Android Studio that understand Gradle DCL files.

NOTE: The build logic and conventions used by declarative and non-declarative projects is currently duplicated. Subsequent milestones/feedback points will bring these back together. 

## Setup

```shell
git clone https://github.com/gradle/nowinandroid.git
cd nowinandroid
git checkout main-declarative
```

You should have this project structure:
```
nowinandroid/
  settings.gradle.dcl
  [...]
```

## Trying things out

### Building the project

You can assemble the project with the following command:

```shell
./gradlew buildDemoDebug
```

### Running tests
**Note:** See the note in [Screenshot tests](README.md#screenshot-tests) about setting up Roborazzi for non-Linux test runs. You may need to run `recordRoborazziDemoDebug` to regenerate screenshots that will work on your machine.

```shell
./gradlew testDemoDebug :lint:test
```

```shell
./gradlew testDemoDebugUnitTest -Proborazzi.test.verify=false
```

After starting a local Android emulator in Android Studio:
```shell
./gradlew connectedDemoDebugAndroidTest --daemon
````

### IDE editing

Syntax highlighting should work in Android Studio nightlies that understand Gradle DCL files.

Code completion and content assist is currently unsupported.

