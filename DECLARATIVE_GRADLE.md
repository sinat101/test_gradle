# Declarative Gradle Prototype

This is a fork of NowInAndroid set up to use

* a branch snapshot of Gradle with the Restricted DSL prototype
* declarative settings file and build files

This is a first cut and this prototype will be amended with more use cases.

You can simply build from the command line or import in Studio.

```shell
./gradlew assembleDebug
```

or import in Studio and use the `app` run configuration to run the app in an emulator.

You can then edit the [declarative settings file](./settings.gradle.something) and [declarative build files](./feature/search/build.gradle.something).

## Declarative DSL

* The `.gradle.something` extension name is temporary.

## Demonstration content

This demonstration includes [declarative settings files](#declarative-settings) and  
[declarative build files](#declarative-build-files).
The Gradle distribution also exports the [declarative DSL schemas](#declarative-dsl-schemas) that
it uses to evaluate the files.

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

    * `fun plugins(configure: PluginDependenciesSpec.() -> Unit)`

* `PluginDependenciesSpec`:
    * `fun id(id: String): PluginDependencySpec`
    * `fun kotlin(id: String): PluginDependencySpec` (this function is not in the Java API)
    * The `alias` functions are not supported yet.

* `PluginDependencySpec`:
    * `fun version(version: String?): PluginDependencySpec` (a builder function)
  * `fun apply(apply: Boolean): PluginDependencySpec` (a builder function)

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
    * `fun <configurationName>(dependency: ProjectDependency)` where `<configurationName>` is any 
       of the names of the configurations registered in the project

* Extensions registered by the plugins, see https://github.com/h0tk3y/restricted-dsl-plugin-schema-demo

### Declarative DSL schemas

When the prototype evaluates declarative settings and build files, it constructs a schema used for 
resolution and validation. 

In the prototype, those schemas are based on Java types present on the Gradle classpath. 
However, once the schemas are built, the evaluation engine can use them without
any dependency on their original types. 

The schemas are serializable, and the current prototype always writes them to 
`.gradle/restricted-schema` during the build.

There schemas that you can find in those directories after a build are:
* for `settings.gradle.something`:
    * `settingsPluginManagement.something.schema` for `pluginManagement { ... }`
    * `settingsPlugins.something.schema` for the plugins DSL, and
    * `settings.something.schema` for the settings file content,
* for `build.gradle.something`:
    * `plugins.something.schema` for the plugins DSL in `build.gradle.something`, and
    * `project.something.schema` for the rest of `build.gradle.something`.