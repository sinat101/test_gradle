androidLibrary {
    namespace = "com.google.samples.apps.nowinandroid.core.data"

    dependencies {
        api(project(":core:common"))
        api(project(":core:database"))
        api(project(":core:datastore"))
        api(project(":core:network"))

        implementation(project(":core:analytics"))
        implementation(project(":core:notifications"))
    }

    kotlinSerialization {
        version = "1.6.3"
        json()
    }

    buildTypes {
        // Need the empty closure to avoid "dangling pure expression" error
        debug {}
        release {}
    }

    testing {
        dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            implementation(project(":core:datastore-test"))
            implementation(project(":core:testing"))
            implementation(project(":core:network"))
        }

        jacoco {
            version = "0.8.7"
        }

        testOptions {
            includeAndroidResources = true
            returnDefaultValues = true
        }
    }
}
