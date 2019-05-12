package dev.whyoleg.kmppm

import groovy.lang.*
import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.Dependency
import org.gradle.util.*
import org.jetbrains.kotlin.gradle.plugin.*

interface DependencyHandler : KotlinDependencyHandler {
    fun add(dependencyNotation: Any): Dependency?
    fun add(dependencyNotation: String, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency
    fun <T : Dependency> add(dependency: T, configure: T.() -> Unit): T
    fun add(dependencyNotation: String, configure: Closure<*>) = add(dependencyNotation) { ConfigureUtil.configure(configure, this) }
    fun <T : Dependency> add(dependency: T, configure: Closure<*>) = add(dependency) { ConfigureUtil.configure(configure, this) }
}

val KotlinDependencyHandler.api: DependencyHandler
    get() = object : DependencyHandler, KotlinDependencyHandler by this {
        override fun add(dependencyNotation: Any): Dependency? = api(dependencyNotation)
        override fun add(dependencyNotation: String, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency =
            api(dependencyNotation, configure)

        override fun <T : Dependency> add(dependency: T, configure: T.() -> Unit): T = api(dependency, configure)
    }

val KotlinDependencyHandler.implementation: DependencyHandler
    get() = object : DependencyHandler, KotlinDependencyHandler by this {
        override fun add(dependencyNotation: Any): Dependency? = implementation(dependencyNotation)
        override fun add(dependencyNotation: String, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency =
            implementation(dependencyNotation, configure)

        override fun <T : Dependency> add(dependency: T, configure: T.() -> Unit): T = implementation(dependency, configure)
    }

val KotlinDependencyHandler.runtimeOnly: DependencyHandler
    get() = object : DependencyHandler, KotlinDependencyHandler by this {
        override fun add(dependencyNotation: Any): Dependency? = runtimeOnly(dependencyNotation)
        override fun add(dependencyNotation: String, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency =
            runtimeOnly(dependencyNotation, configure)

        override fun <T : Dependency> add(dependency: T, configure: T.() -> Unit): T = runtimeOnly(dependency, configure)
    }

val KotlinDependencyHandler.compileOnly: DependencyHandler
    get() = object : DependencyHandler, KotlinDependencyHandler by this {
        override fun add(dependencyNotation: Any): Dependency? = compileOnly(dependencyNotation)
        override fun add(dependencyNotation: String, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency =
            compileOnly(dependencyNotation, configure)

        override fun <T : Dependency> add(dependency: T, configure: T.() -> Unit): T = compileOnly(dependency, configure)
    }
