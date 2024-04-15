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

package org.gradle.api.experimental.android.nowinandroid;

import org.gradle.api.Plugin;
import org.gradle.api.experimental.android.ConventionalAndroidHiltJacocoPlugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.internal.plugins.software.RegistersSoftwareTypes;

@SuppressWarnings("UnstableApiUsage") // For RegistersSoftwareTypes
@RegistersSoftwareTypes({ConventionalAndroidHiltJacocoPlugin.class})
public abstract class NowInAndroidConventionsPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings target) {
        // No-Op, but not removable, otherwise:
        // > Could not generate a decorated class for type NowInAndroidConventionsPlugin.
        //   > Cannot have abstract method Plugin.apply().
    }
}
