package dev.whyoleg.kmppm

import dev.whyoleg.kmppm.base.*
import dev.whyoleg.kmppm.base.DependenciesConfigurationType.*
import dev.whyoleg.kmppm.base.Target
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

//import base.*
//import base.Target
//import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
//import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
//
//@DslMarker
//annotation class DependencyContextMarker
//
//@DependencyContextMarker

fun KotlinMultiplatformExtension.sources(preTargets: Set<Target>, builder: SourcesBuilder.() -> Unit) {
    val preSources = SourcesBuilder().apply(builder).sources(preTargets.sourceSet("common"))
    val allTargets = preSources.targets()
    val mainSources = preSources.copy(sourceSet = allTargets.sourceSet("common"))
    allTargets.forEach { it.configure(this, it) }

    fun SourceSet.kotlin(type: SourceType): KotlinSourceSet =
        sourceSets.maybeCreate(name + type.name.capitalize())

    fun Sources.sourceSet(type: SourceType): KotlinSourceSet? = sourceSet.name?.let { sourceSet.kotlin(type) }

    fun Sources.resolveStructure() {
        SourceType.values().forEach { type ->
            sourceSet(type)?.let { kotlinSourceSet ->
                dependentSources.forEach {
                    it.sourceSet(type)?.let { dependentKotlinSourceSet ->
                        if (dependentKotlinSourceSet.name != kotlinSourceSet.name)
                            dependentKotlinSourceSet.dependsOn(kotlinSourceSet)
                    }
                }
            }
        }
    }

    fun Sources.configure() {
        resolveStructure()
        sourceConfigurations.forEach { (type, configurations) ->
            val kotlinSourceSet = sourceSet(type)
            when {
                kotlinSourceSet == null || kotlinSourceSet.name == "common${type.name.capitalize()}" ->
                    configurations.forEach { (confType, deps) ->
                        deps.flatMap { it.artifacts.entries }
                            .groupBy { it.key }
                            .forEach { (target, artifacts) ->
                                SourceSet(target).kotlin(type)
                                    .dependencies { this[confType] = artifacts.map { it.value } }
                            }
                    }
                else -> kotlinSourceSet.dependencies {
                    configurations.forEach { (confType, deps) ->
                        if (sourceSet.targets.size > 1 &&
                            deps.flatMap { it.artifacts.keys }.distinctBy { it.type }.size > 1
                        ) error("Mixed dependencies for different platforms")
                        this[confType] = deps.flatMap { it.artifacts.values }
                    }
                }
            }
        }
        dependentSources.forEach(Sources::configure)
    }

    mainSources.configure()
}

operator fun KotlinDependencyHandler.set(type: DependenciesConfigurationType, dependencies: Iterable<Artifact>) {
    val helper = helper()
    when (type) {
        implementation -> dependencies.forEach { helper.implementation.artifact(it) }
        api -> dependencies.forEach { helper.api.artifact(it) }
        runtimeOnly -> dependencies.forEach { helper.runtimeOnly.artifact(it) }
        compileOnly -> dependencies.forEach { helper.compileOnly.artifact(it) }
    }
}
// {
//
//
//
//}

//
//@DependencyContextMarker
class TargetDependencyBuilder(
    private val sourceSet: KotlinSourceSet
) {

//    operator fun DependencyConverter.invoke(dependency: Dependency) {
//        dependency.artifacts.forEach { (target, provider) ->
//            if (sourceSet.name.endsWith("Main")) {
//            }
//        }
//
//        if (sourceSet.name.endsWith("Main")) {
//            sourceSet.dependencies {
//                println(dependency)
//                println(target)
//                println()
//                dependency.artifacts[target]?.invoke(this@invoke()) ?: error("No Target")
//            }
//        }
//    }
//
//    operator fun DependencyConverter.invoke(dependencies: Set<Dependency>) {
//        dependencies.forEach { invoke(it) }
//    }
//
//    operator fun DependencyConverter.invoke(vararg dependencies: Dependency) {
//        dependencies.forEach { invoke(it) }
//    }
//
//    operator fun DependencyConverter.invoke(builder: DependencyClosure.() -> Unit) {
//        invoke(DependencyClosure().apply(builder).dependencies)
//    }
}
//
//inline class DependencyClosure(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
//    operator fun Dependency.unaryPlus() {
//        dependencies += this
//    }
//}
//

//
//val test: KotlinMultiplatformExtension.() -> Unit = {
//    val test = Dependency("") {
//        val dep = Maven("", "")
//        Target.META use dep.copy(postfix = "common")
//        Target.JVM use dep
//        (Target.JVM + Target.JVM + Target.META) use dep
//
//        Target.JVM use Ignored
//    }
//    sources(setOf(Target.JVM)) {
//        main {
//            implementation(test)
//            implementation(test + test)
//            implementation(test, test)
//            implementation {
//                +test
//            }
//        }
//        Target.JVM.sources {
//            main {
//                implementation(test)
//                implementation(test + test)
//                implementation(test, test)
//                implementation {
//                    +test
//                }
//            }
//        }
//    }
//}