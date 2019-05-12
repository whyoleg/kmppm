package dev.whyoleg.kmppm

import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

class SourceSetBuilder(
    private val target: NamedTarget,
    private val kotlin: KotlinMultiplatformExtension
) {
    private val main = kotlin.sourceSets.maybeCreate(target.name + "Main")
    fun main(block: TargetDependencyBuilder.() -> Unit): Unit = TargetDependencyBuilder(target, main).block()

    private val test = kotlin.sourceSets.maybeCreate(target.name + "Test")
    fun test(block: TargetDependencyBuilder.() -> Unit): Unit = TargetDependencyBuilder(target, test).block()

    fun NamedTarget.configure(build: SourceSetBuilder.() -> Unit) {
        val newSet = SourceSetBuilder(this, kotlin)
        if (newSet.target.name != target.name) {
            newSet.main.dependsOn(main)
            newSet.test.dependsOn(test)
        }
        newSet.apply(build)
    }
}

class TargetDependencyBuilder(
    private val target: NamedTarget,
    private val sourceSet: KotlinSourceSet
) {
    private inline fun internal(dependency: Dependency, crossinline dependencyHandler: KotlinDependencyHandler.() -> DependencyHandler) {
        sourceSet.dependencies {
            dependency.providers[target]?.invoke(dependencyHandler()) ?: error("No Target")
        }
    }

    private inline fun internal(
        dependencies: Set<Dependency>,
        crossinline dependencyHandler: KotlinDependencyHandler.() -> DependencyHandler
    ) {
        dependencies.forEach { internal(it, dependencyHandler) }
    }

    private inline fun internal(
        builder: MultipleDependencyBuilder.() -> Unit,
        crossinline dependencyHandler: KotlinDependencyHandler.() -> DependencyHandler
    ) {
        internal(MultipleDependencyBuilder().apply(builder).dependencies, dependencyHandler)
    }

    fun implementation(dependency: Dependency): Unit = internal(dependency) { implementation }
    fun implementation(dependencies: Set<Dependency>): Unit = internal(dependencies) { implementation }
    fun implementation(vararg dependencies: Dependency): Unit = internal(dependencies.toSet()) { implementation }
    fun implementation(builder: MultipleDependencyBuilder.() -> Unit): Unit = internal(builder) { implementation }

    fun api(dependency: Dependency): Unit = internal(dependency) { api }
    fun api(builder: MultipleDependencyBuilder.() -> Unit): Unit = internal(builder) { api }
}

class MultipleDependencyBuilder {
    internal val dependencies = mutableSetOf<Dependency>()
    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}

fun KotlinMultiplatformExtension.configure(build: SourceSetBuilder.() -> Unit) {
    SourceSetBuilder(Target.META, this).build()
}

val test: KotlinMultiplatformExtension.() -> Unit = {
    val test = Dependency("") {
        val dep = Maven("", "")
        Target.META with dep.copy(postfix = "common")
        Target.JVM with dep
        (Target.JVM + Target.JVM + Target.META) with dep

        Target.JVM with Ignored
    }
    configure {
        main {
            implementation(test)
            implementation(test + test)
            implementation(test, test)
            implementation {
                +test
            }
        }
    }
}