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

package org.gradle.api.experimental.android;

import org.gradle.api.experimental.android.library.AndroidLibrary;
import org.gradle.api.experimental.android.library.AndroidLibraryBuildTypes;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.declarative.dsl.model.annotations.Restricted;

import javax.inject.Inject;

@Restricted
public abstract class ConventionalAndroidHiltJacocoLibrary implements AndroidLibrary {
    private final KSPAndroidLibraryDependencies dependencies;
    private final AndroidLibraryBuildTypes buildTypes;

    @Inject
    public ConventionalAndroidHiltJacocoLibrary() {
        this.buildTypes = getObjectFactory().newInstance(AndroidLibraryBuildTypes.class);
        this.dependencies = getObjectFactory().newInstance(KSPAndroidLibraryDependencies.class);
    }

    @Inject
    public abstract ObjectFactory getObjectFactory();

    @Restricted
    public abstract Property<Boolean> getIncludeKotlinSerialization();

    /**
     * Static targets extension block.
     */
    @Override
    public AndroidLibraryBuildTypes getBuildTypes() {
        return buildTypes;
    }

    /**
     * Common dependencies for all targets.
     */
    @Override
    public KSPAndroidLibraryDependencies getDependencies() {
        return dependencies;
    }
}
