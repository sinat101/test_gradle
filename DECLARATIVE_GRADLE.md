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

## Demonstration content

This demonstration includes [declarative settings files](#declarative-settings) and  
[declarative build files](#declarative-build-files).

* The available models are currently taken from the current Gradle API.
* Error reporting is not user friendly but displays informed messages

### Declarative settings

* Enum properties such as `dependencyResolutionManagement.repositoriesMode` are currently unsupported.

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

### Declarative build files

See [`feature/search/build.gradle.something`](feature/search/build.gradle.something) for an example.

In command line builds, check the result with:

```shell
./gradlew :feature:search:dependencyInsight --configuration prodDebugCompileClasspath --dependency feature
```

Currently, only plugins and project dependencies are supported. Everything else should
be moved to convention plugins.

The plugins and the rest of a build file are interpreted in two separate steps, similar to 
the other DSLs.

Supported APIs for plugins declaration:

* On the top level:
    * `fun plugins(configure: PluginDependenciesSpec.() -> Unit)`

* `PluginDependenciesSpec`:
    * `fun id(id: String): PluginDependencySpec`
    * `fun kotlin(id: String): PluginDependencySpec` (this function is not in the Java API)
    * The `alias` functions are not supported yet.

* `PluginDependencySpec`:
    * `fun version(version: String?): PluginDependencySpec` (a builder function)
    * `fun apply(apply: Boolean): PluginDependencySpec` (a builder function)

Supported APIs for project evaluation:

* `Project`:
    * `fun dependencies(configure: DependencyHandler.() -> Unit)>)`
        * (+ `val dependencies`)
    * `fun project(path: String): ProjectDependency`

* `DependencyHandler`:
    * `fun api(dependency: ProjectDependency)`
    * `fun compileOnly(dependency: ProjectDependency)`
    * `fun implementation(dependency: ProjectDependency)`
    * `fun testImplementation(dependency: ProjectDependency)`
    * `fun androidTestImplementation(dependency: ProjectDependency)`
    * These configurations are hardcoded for now.