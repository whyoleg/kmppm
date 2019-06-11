package dev.whyoleg.kamp

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.module.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.plugin.*

interface DependencyHandler : KotlinDependencyHandler {
    fun add(artifact: Any)
}

private fun KotlinDependencyHandler.handler(type: DependencySetType) = DependencyHelper.byType(type, this)

fun KotlinDependencyHandler.modules(type: DependencySetType, dependencies: Iterable<Module>) {
    val handler = handler(type)
    dependencies.forEach { (name) ->
        println("Add $name")
        handler.add(handler.project(name))
    }
}

fun KotlinDependencyHandler.packages(type: DependencySetType, dependencies: Iterable<PackageDependency>, target: Target) {
    val handler = handler(type)
    dependencies.forEach { dependency ->
        val (_, rawPostfix) = dependency.targets.find { it.target.name == target.name } ?: return
        val dep = dependency.raw.string(rawPostfix)
        println("Add '$dep'")
        handler.add(dep)
    }
}

fun KotlinDependencyHandler.libraries(type: DependencySetType, dependencies: Iterable<LibraryDependency>, project: Project) {
    val handler = handler(type)
    dependencies.forEach { (path, isFolder) ->
        val folder = if (isFolder) "folder" else ""
        println("Add $folder $path")
        if (isFolder) {
            handler.add(project.fileTree(path))
        } else {
            handler.add(path)
        }
    }
}

private typealias DependencyConverter = KotlinDependencyHandler.() -> DependencyHandler

private object DependencyHelper {
    val api: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                api(artifact)
            }
        }
    }

    val implementation: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                implementation(artifact)
            }
        }
    }

    val runtimeOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                runtimeOnly(artifact)
            }
        }
    }

    val compileOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                compileOnly(artifact)
            }
        }
    }

    fun byType(type: DependencySetType, handler: KotlinDependencyHandler) = when (type) {
        DependencySetType.implementation -> implementation(handler)
        DependencySetType.api            -> api(handler)
        DependencySetType.runtimeOnly    -> runtimeOnly(handler)
        DependencySetType.compileOnly    -> compileOnly(handler)
    }

}