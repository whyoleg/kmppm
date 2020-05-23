package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependencyHandler {
    fun api(dependency: KampModule): Dependency?
    fun api(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun api(dependency: KampDependency): Dependency?
    fun api(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun implementation(dependency: KampModule): Dependency?
    fun implementation(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun implementation(dependency: KampDependency): Dependency?
    fun implementation(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun compileOnly(dependency: KampModule): Dependency?
    fun compileOnly(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun compileOnly(dependency: KampDependency): Dependency?
    fun compileOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?

    fun runtimeOnly(dependency: KampModule): Dependency?
    fun runtimeOnly(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency?
    fun runtimeOnly(dependency: KampDependency): Dependency?
    fun runtimeOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?
}

@PublishedApi
internal class KampPlatformDependencyHandler(
    private val platformType: KotlinPlatformType,
    private val handler: KotlinDependencyHandler
) : KampDependencyHandler {

    override fun api(dependency: KampModule): Dependency? =
        handler.api(handler.project(dependency.name))

    override fun api(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.api(handler.project(dependency.name), configure)

    override fun api(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::api)

    override fun api(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.api(it, configure) }


    override fun implementation(dependency: KampModule): Dependency? =
        handler.implementation(handler.project(dependency.name))

    override fun implementation(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.implementation(handler.project(dependency.name), configure)

    override fun implementation(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::implementation)

    override fun implementation(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.implementation(it, configure) }


    override fun compileOnly(dependency: KampModule): Dependency? =
        handler.compileOnly(handler.project(dependency.name))

    override fun compileOnly(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.compileOnly(handler.project(dependency.name), configure)

    override fun compileOnly(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::compileOnly)

    override fun compileOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.compileOnly(it, configure) }


    override fun runtimeOnly(dependency: KampModule): Dependency? =
        handler.runtimeOnly(handler.project(dependency.name))

    override fun runtimeOnly(dependency: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.runtimeOnly(handler.project(dependency.name), configure)

    override fun runtimeOnly(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::runtimeOnly)

    override fun runtimeOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.runtimeOnly(it, configure) }
}
