package dev.whyoleg.kmppm

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@DslMarker
annotation class DependencyContextMarker

@DependencyContextMarker
class SourceSetBuilder(
    private val target: NamedTarget,
    private val kotlin: KotlinMultiplatformExtension
) {
    private val main = kotlin.sourceSets.maybeCreate(target.name + "Main")
    fun main(block: TargetDependencyBuilder.() -> Unit): Unit = TargetDependencyBuilder(main).block()

    private val test = kotlin.sourceSets.maybeCreate(target.name + "Test")
    fun test(block: TargetDependencyBuilder.() -> Unit): Unit = TargetDependencyBuilder(test).block()

    init {
        if (target is TargetSet) {
            target.targets().forEach {
                it.sources {
                    this.main.dependsOn(this@SourceSetBuilder.main)
                    this.test.dependsOn(this@SourceSetBuilder.test)
                }
            }
        }
    }

    fun NamedTarget.sources(build: SourceSetBuilder.() -> Unit) {
        val newSet = SourceSetBuilder(this, kotlin)
        if (newSet.target.name != target.name) {
            newSet.main.dependsOn(main)
            newSet.test.dependsOn(test)
        }
        newSet.apply(build)
    }
}

@DependencyContextMarker
class TargetDependencyBuilder(
    private val sourceSet: KotlinSourceSet
) : DependencyHelper {

    operator fun DependencyConverter.invoke(dependency: Dependency) {
        dependency.providers.forEach { (target, provider) ->
            if (sourceSet.name.endsWith("Main")) {
            }
        }

        if (sourceSet.name.endsWith("Main")) {
            sourceSet.dependencies {
                println(dependency)
                println(target)
                println()
                dependency.providers[target]?.invoke(this@invoke()) ?: error("No Target")
            }
        }
    }

    operator fun DependencyConverter.invoke(dependencies: Set<Dependency>) {
        dependencies.forEach { invoke(it) }
    }

    operator fun DependencyConverter.invoke(vararg dependencies: Dependency) {
        dependencies.forEach { invoke(it) }
    }

    operator fun DependencyConverter.invoke(builder: MultipleDependencyBuilder.() -> Unit) {
        invoke(MultipleDependencyBuilder().apply(builder).dependencies)
    }
}

inline class MultipleDependencyBuilder(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}

fun KotlinMultiplatformExtension.sources(targets: Set<Target>, build: SourceSetBuilder.() -> Unit) {
    targets.forEach { it.config(this) }
    SourceSetBuilder(targets.named("common"), this).build()
}

val test: KotlinMultiplatformExtension.() -> Unit = {
    val test = Dependency("") {
        val dep = Maven("", "")
        Target.META with dep.copy(postfix = "common")
        Target.JVM with dep
        (Target.JVM + Target.JVM + Target.META) with dep

        Target.JVM with Ignored
    }
    sources(setOf(Target.JVM)) {
        main {
            implementation(test)
            implementation(test + test)
            implementation(test, test)
            implementation {
                +test
            }
        }
        Target.JVM.sources {
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
}