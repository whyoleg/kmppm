package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*

interface KampDependencyHandler {

    fun api(module: KampModule): Dependency?
    fun api(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun api(dependency: KampDependency): Dependency?
    fun api(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun implementation(module: KampModule): Dependency?
    fun implementation(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun implementation(dependency: KampDependency): Dependency?
    fun implementation(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun compileOnly(module: KampModule): Dependency?
    fun compileOnly(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun compileOnly(dependency: KampDependency): Dependency?
    fun compileOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun runtimeOnly(module: KampModule): Dependency?
    fun runtimeOnly(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun runtimeOnly(dependency: KampDependency): Dependency?
    fun runtimeOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

}
