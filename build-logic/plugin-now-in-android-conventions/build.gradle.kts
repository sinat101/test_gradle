/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.google.samples.apps.nowinandroid.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":plugin-android-hilt-jacoco"))

    // TODO: These were apply-falses at the build level previously,
    // but now can't be added to the plugins block because of the error:
    // The request for this plugin could not be satisfied because the plugin is already on the classpath with an unknown version, so compatibility cannot be checked.
    // Adding them here to remember them
    api("com.android.application:com.android.application.gradle.plugin:8.2.0")
    api("com.android.library:com.android.library.gradle.plugin:8.2.0")
    api("com.android.test:com.android.test.gradle.plugin:8.2.0")
}

gradlePlugin {
    plugins {
        register("now-in-android-conventions") {
            id = "org.gradle.experimental.now-in-android-conventions"
            implementationClass = "org.gradle.api.experimental.android.nowinandroid.NowInAndroidConventionsPlugin"
        }
    }
}
