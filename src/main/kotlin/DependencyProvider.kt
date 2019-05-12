package dev.whyoleg.kmppm

import dev.whyoleg.kmppm.Target.Companion.ALL
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

fun DependencyProvider.on(target: Target): dev.whyoleg.kmppm.Dependency = Dependency {
    ALL with Ignored
    target with this@on
}