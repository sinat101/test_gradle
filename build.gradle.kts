/*
 * Copyright 2021 The Android Open Source Project
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

buildscript {
    repositories {
        google()
        mavenCentral()

        // Android Build Server
        maven { url = uri("../nowinandroid-prebuilts/m2repository") }
    }
    dependencies {
        classpath(libs.google.oss.licenses.plugin) {
            exclude(group = "com.google.protobuf")

            constraints {
                add("classpath", "com.android.tools.build:gradle:8.3.0") {
                    because("Aligns the resolved versions of the com.android plugins, prevents: \n" +
                        "Script compilation errors:\n" +
                        "  Line 62:     packaging {\n" +
                        "               ^ Unresolved reference: packaging\n")
                }
            }
        }
    }

}

// Lists all plugins used throughout the project
plugins {
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.android.library.get().pluginId) apply false
    id(libs.plugins.android.test.get().pluginId) apply false
    id(libs.plugins.baselineprofile.get().pluginId) apply false
    id(libs.plugins.kotlin.jvm.get().pluginId) apply false
    id(libs.plugins.kotlin.serialization.get().pluginId) apply false
    id(libs.plugins.dependencyGuard.get().pluginId) apply false
    id(libs.plugins.firebase.crashlytics.get().pluginId) apply false
    id(libs.plugins.firebase.perf.get().pluginId) apply false
    id(libs.plugins.gms.get().pluginId) apply false
    id(libs.plugins.hilt.get().pluginId) apply false
    id(libs.plugins.ksp.get().pluginId) apply false
    id(libs.plugins.roborazzi.get().pluginId) apply false
    id(libs.plugins.secrets.get().pluginId) apply false
    id(libs.plugins.room.get().pluginId) apply false
    id(libs.plugins.module.graph.get().pluginId) apply true // Plugin applied to allow module graph generation
}

// Task to print all the module paths in the project e.g. :core:data
// Used by module graph generator script
tasks.register("printModulePaths") {
    subprojects {
        if (subprojects.size == 0) {
            println(this.path)
        }
    }
}
