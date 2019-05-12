package dev.whyoleg.kmppm

import org.gradle.api.artifacts.Dependency
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

interface DependencyHandler : KotlinDependencyHandler {
    fun add(dependencyNotation: Any): Dependency?
}

typealias DependencyConverter = KotlinDependencyHandler.() -> DependencyHandler

interface DependencyHelper {
    val api: DependencyConverter get() = Companion.api
    val implementation: DependencyConverter get() = Companion.implementation
    val runtimeOnly: DependencyConverter get() = Companion.runtimeOnly
    val compileOnly: DependencyConverter get() = Companion.compileOnly

    companion object {
        val api: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(dependencyNotation: Any): Dependency? = api(dependencyNotation)
            }
        }

        private val implementation: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(dependencyNotation: Any): Dependency? = implementation(dependencyNotation)
            }
        }

        private val runtimeOnly: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(dependencyNotation: Any): Dependency? = runtimeOnly(dependencyNotation)
            }
        }

        private val compileOnly: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(dependencyNotation: Any): Dependency? = compileOnly(dependencyNotation)
            }
        }
    }
}
