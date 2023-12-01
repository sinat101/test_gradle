# Declarative Gradle Prototype

This is a fork of NowInAndroid set up to use

* a branch snapshot of Gradle with the Restricted DSL prototype
* a declarative settings file

This is a first cut and this prototype will be amended with more use cases.

You can simply build from the command line or import in Studio.

```shell
./gradlew assembleDebug
```

or import in Studio and use the `app` run configuration to run the app in an emulator.

You can then edit the [declarative settings file](./settings.gradle.something).

## Restricted DSL

* The `.gradle.something` extension name is temporary.

### Declarative settings

* The available model is currently taken from the current Gradle API.
* Enum properties such as `dependencyResolutionManagement.repositoriesMode` are currently unsupported.
* Error reporting is not user friendly but displays informed messages.

Supported APIs in `settings.gradle.something`:

* `Settings`:
    * `val rootProject: ProjectDescriptor`
    * `include(projectPath: String)` (the `vararg` overload is not supported)
    * `pluginManagement(pluginManagementSpec: PluginManagementSpec.() -> Unit)`
        * (+ `val pluginManagement`)
    * `dependencyResolutionManagement(dependencyResolutionConfiguration: DependencyResolutionManagement.() -> Unit)`
        * (+ `val dependencyResolutionManagement`)
    * `enableFeaturePreview(name: String)`

* `ProjectDescriptor`:
    * `var name: String`

* `RepositoryHandler`:
    * `gradlePluginPortal()`
    * `mavenCentral()`
    * `google()`

* `PluginManagementSpec`:
    * `repositories(repositoriesAction: RepositoriesHandler.() -> Unit)`
        * (+ `val repositories`)
    * `includeBuild(rootProject: String)` (the overload with the lambda is not supported)

* `DependencyResolutionManagement`:
    * `repositories(repositoriesAction: RepositoriesHandler.() -> Unit)`
        * (+ `val repositories`)
