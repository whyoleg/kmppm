package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependencyHandler {
    fun api(dependencyNotation: KampDependency): Dependency?
    fun api(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun implementation(dependencyNotation: KampDependency): Dependency?
    fun implementation(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun compileOnly(dependencyNotation: KampDependency): Dependency?
    fun compileOnly(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun runtimeOnly(dependencyNotation: KampDependency): Dependency?
    fun runtimeOnly(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?
}

@PublishedApi
internal class PlatformKampDependencyHandler(
    private val platformType: KotlinPlatformType,
    private val handler: KotlinDependencyHandler
) : KampDependencyHandler {
    override fun api(dependencyNotation: KampDependency): Dependency? =
        dependencyNotation.notation(platformType)?.let(handler::api)

    override fun api(
        dependencyNotation: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependencyNotation.notation(platformType)?.let { handler.api(it, configure) }

    override fun implementation(dependencyNotation: KampDependency): Dependency? =
        dependencyNotation.notation(platformType)?.let(handler::implementation)

    override fun implementation(
        dependencyNotation: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependencyNotation.notation(platformType)?.let { handler.implementation(it, configure) }

    override fun compileOnly(dependencyNotation: KampDependency): Dependency? =
        dependencyNotation.notation(platformType)?.let(handler::compileOnly)

    override fun compileOnly(
        dependencyNotation: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependencyNotation.notation(platformType)?.let { handler.compileOnly(it, configure) }

    override fun runtimeOnly(dependencyNotation: KampDependency): Dependency? =
        dependencyNotation.notation(platformType)?.let(handler::runtimeOnly)

    override fun runtimeOnly(
        dependencyNotation: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependencyNotation.notation(platformType)?.let { handler.runtimeOnly(it, configure) }
}
