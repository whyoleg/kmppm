package dev.whyoleg.kmppm

import org.gradle.api.artifacts.Dependency

typealias DependencyProvider = DependencyHandler.() -> Dependency?

data class Maven(
    val group: String,
    val artifact: String,
    val version: String? = null,
    val postfix: String? = null
) : (DependencyHandler) -> Dependency? {
    override fun invoke(handler: DependencyHandler): Dependency? =
        handler.add("$group:$artifact${postfix?.let { "-$it" } ?: ""}${version?.let { ":$it" } ?: ""}")
}

val Ignored: DependencyProvider = { null }

fun Module(name: String, configuration: String? = null): DependencyProvider = { add(project(name, configuration)) }
