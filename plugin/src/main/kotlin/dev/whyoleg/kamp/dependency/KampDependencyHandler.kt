package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

interface KampDependencyHandler {
    fun api(dependency: KampProjectDependency): Dependency?
    fun api(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun api(dependency: KampDependency): Dependency?
    fun api(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun implementation(dependency: KampProjectDependency): Dependency?
    fun implementation(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun implementation(dependency: KampDependency): Dependency?
    fun implementation(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun compileOnly(dependency: KampProjectDependency): Dependency?
    fun compileOnly(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun compileOnly(dependency: KampDependency): Dependency?
    fun compileOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun runtimeOnly(dependency: KampProjectDependency): Dependency?
    fun runtimeOnly(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun runtimeOnly(dependency: KampDependency): Dependency?
    fun runtimeOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?
}

@PublishedApi
internal class PlatformKampDependencyHandler(
    private val platformType: KotlinPlatformType, private val handler: KotlinDependencyHandler
) : KampDependencyHandler {

    @Suppress("NOTHING_TO_INLINE")
    private inline fun provide(dependency: KampDependency) {
        //TODO setup some configuration for it?
        dependency.provider?.let {
            it((handler as DefaultKotlinDependencyHandler).project.repositories)
        }
    }

    override fun api(dependency: KampProjectDependency): Dependency? = handler.api(handler.project(dependency.name))
    override fun api(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.api(handler.project(dependency.name), configure)

    override fun api(dependency: KampDependency): Dependency? = dependency.also(::provide).notation(platformType)?.let(handler::api)

    override fun api(
        dependency: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependency.also(::provide).notation(platformType)?.let { handler.api(it, configure) }


    override fun implementation(dependency: KampProjectDependency): Dependency? = handler.implementation(handler.project(dependency.name))
    override fun implementation(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.implementation(handler.project(dependency.name), configure)

    override fun implementation(dependency: KampDependency): Dependency? =
        dependency.also(::provide).notation(platformType)?.let(handler::implementation)

    override fun implementation(
        dependency: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependency.also(::provide).notation(platformType)?.let { handler.implementation(it, configure) }


    override fun compileOnly(dependency: KampProjectDependency): Dependency? = handler.compileOnly(handler.project(dependency.name))
    override fun compileOnly(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.compileOnly(handler.project(dependency.name), configure)

    override fun compileOnly(dependency: KampDependency): Dependency? =
        dependency.also(::provide).notation(platformType)?.let(handler::compileOnly)

    override fun compileOnly(
        dependency: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependency.also(::provide).notation(platformType)?.let { handler.compileOnly(it, configure) }


    override fun runtimeOnly(dependency: KampProjectDependency): Dependency? = handler.runtimeOnly(handler.project(dependency.name))
    override fun runtimeOnly(dependency: KampProjectDependency, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.runtimeOnly(handler.project(dependency.name), configure)

    override fun runtimeOnly(dependency: KampDependency): Dependency? =
        dependency.also(::provide).notation(platformType)?.let(handler::runtimeOnly)

    override fun runtimeOnly(
        dependency: KampDependency,
        configure: ExternalModuleDependency.() -> Unit
    ): ExternalModuleDependency? = dependency.also(::provide).notation(platformType)?.let { handler.runtimeOnly(it, configure) }
}
