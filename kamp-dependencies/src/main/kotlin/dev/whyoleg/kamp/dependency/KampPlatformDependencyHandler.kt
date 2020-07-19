package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.plugin.*

@PublishedApi
internal class KampPlatformDependencyHandler(
    private val platformType: KotlinPlatformType,
    private val handler: KotlinDependencyHandler
) : KampDependencyHandler {

    override fun api(module: KampModule): Dependency? =
        handler.api(handler.project(module.name))

    override fun api(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.api(handler.project(module.name), configure)

    override fun api(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::api)

    override fun api(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.api(it, configure) }


    override fun implementation(module: KampModule): Dependency? =
        handler.implementation(handler.project(module.name))

    override fun implementation(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.implementation(handler.project(module.name), configure)

    override fun implementation(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::implementation)

    override fun implementation(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.implementation(it, configure) }


    override fun compileOnly(module: KampModule): Dependency? =
        handler.compileOnly(handler.project(module.name))

    override fun compileOnly(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.compileOnly(handler.project(module.name), configure)

    override fun compileOnly(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::compileOnly)

    override fun compileOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.compileOnly(it, configure) }


    override fun runtimeOnly(module: KampModule): Dependency? =
        handler.runtimeOnly(handler.project(module.name))

    override fun runtimeOnly(module: KampModule, configure: ProjectDependency.() -> Unit): ProjectDependency =
        handler.runtimeOnly(handler.project(module.name), configure)

    override fun runtimeOnly(dependency: KampDependency): Dependency? =
        dependency.notation(platformType)?.let(handler::runtimeOnly)

    override fun runtimeOnly(dependency: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependency.notation(platformType)?.let { handler.runtimeOnly(it, configure) }
}
