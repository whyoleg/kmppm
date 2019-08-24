package dev.whyoleg.kamp.dependency.configuration

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.plugin.*

private val api: KotlinDependencyHandler.(Any) -> Unit = { api(it) }
private val implementation: KotlinDependencyHandler.(Any) -> Unit = { implementation(it) }
private val runtimeOnly: KotlinDependencyHandler.(Any) -> Unit = { runtimeOnly(it) }
private val compileOnly: KotlinDependencyHandler.(Any) -> Unit = { compileOnly(it) }

private fun DependencyConfigurationType.handler(): KotlinDependencyHandler.(Any) -> Unit = when (this) {
    DependencyConfigurationType.api            -> api
    DependencyConfigurationType.implementation -> implementation
    DependencyConfigurationType.runtimeOnly    -> runtimeOnly
    DependencyConfigurationType.compileOnly    -> compileOnly
}

internal fun KotlinDependencyHandler.modules(type: DependencyConfigurationType, dependencies: Iterable<ModuleDependency>) {
    val handler = type.handler()
    dependencies.forEach { (name) ->
        handler(project(name))
    }
}

internal fun KotlinDependencyHandler.packages(
    versions: BuiltInVersions,
    type: DependencyConfigurationType,
    dependencies: Iterable<PackageDependency>,
    target: Target
) {
    val handler = type.handler()
    dependencies.forEach { dependency ->
        val (_, rawPostfix) = dependency.targets.find { it.target.name == target.name } ?: return
        val dep = dependency.raw.string(versions, rawPostfix)
        handler(dep)
    }
}

internal fun KotlinDependencyHandler.libraries(type: DependencyConfigurationType, dependencies: Iterable<LibraryDependency>, project: Project) {
    val handler = type.handler()
    dependencies.forEach { (path, isFolder) ->
        if (isFolder) handler(project.fileTree(path)) else handler(path)
    }
}