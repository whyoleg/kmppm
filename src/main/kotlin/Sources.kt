package dev.whyoleg.kmppm

import dev.whyoleg.kmppm.base.*
import dev.whyoleg.kmppm.base.DependenciesConfigurationType.*
import dev.whyoleg.kmppm.base.SourceType.main
import dev.whyoleg.kmppm.base.SourceType.test
import dev.whyoleg.kmppm.base.Target
import dev.whyoleg.kmppm.base.Target.Companion.Android
import dev.whyoleg.kmppm.base.Target.Companion.Common
import dev.whyoleg.kmppm.base.Target.Companion.Js
import dev.whyoleg.kmppm.base.Target.Companion.Jvm
import dev.whyoleg.kmppm.base.Target.Companion.LinuxX64
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

//targets

//common - deps
//platform - deps + artifacts

@DslMarker
annotation class MagicDSL

@MagicDSL
fun KotlinMultiplatformExtension.targets(targets: Iterable<Target>) = targets.forEach { it.configure(this, it) }

@MagicDSL
fun KotlinMultiplatformExtension.common(builder: SourceConfigurationBuilder.() -> Unit) {
    val data = SourceConfigurationBuilder().apply(builder).data()

    val targets = data
        .flatMap { it.dependencyConfigurations }
        .flatMap { it.dependencies }
        .flatMap { it.artifacts.keys }

    targets(targets)

    data.forEach { (sourceType, list) ->
        val map = targets.associateWith {
            sourceSets.maybeCreate(it.name + sourceType.name.capitalize())
        }
        list.forEach { (type, dependencies) ->
            dependencies
                .flatMap { it.artifacts.entries }
                .groupBy { it.key }
                .mapValues { it.value.mapNotNull { it.value } }
                .forEach { (target, deps) ->
                    (map[target] ?: error("No target")).dependencies { this[type] = deps }
                }
        }
    }
}

@MagicDSL
fun KotlinMultiplatformExtension.sourceSets(builder: SourcesBuilder.() -> Unit) {

}

//fun KotlinMultiplatformExtension.sources(preTargets: Set<Target>, builder: SourcesBuilder.() -> Unit) {
//    val preSources = SourcesBuilder().apply(builder).sources(preTargets.sourceSet("common"))
//    val allTargets = preSources.targets()
//    val mainSources = preSources.copy(sourceSet = allTargets.sourceSet("common"))
//    allTargets.forEach { it.configure(this, it) }
//
//    fun SourceSet.kotlin(type: SourceType): KotlinSourceSet =
//        sourceSets.maybeCreate(name + type.name.capitalize())
//
//    fun Sources.sourceSet(type: SourceType): KotlinSourceSet? = sourceSet.name?.let { sourceSet.kotlin(type) }
//
//    fun Sources.resolveStructure() {
//        values().forEach { type ->
//            sourceSet(type)?.let { kotlinSourceSet ->
//                dependentSources.forEach {
//                    it.sourceSet(type)?.let { dependentKotlinSourceSet ->
//                        if (dependentKotlinSourceSet.name != kotlinSourceSet.name)
//                            dependentKotlinSourceSet.dependsOn(kotlinSourceSet)
//                    }
//                }
//            }
//        }
//    }
//
//    fun Sources.configure() {
//        resolveStructure()
//        sourceConfigurations.forEach { (type, configurations) ->
//            val kotlinSourceSet = sourceSet(type)
//            when {
//                kotlinSourceSet == null || kotlinSourceSet.name == "common${type.name.capitalize()}" ->
//                    configurations.forEach { (confType, deps) ->
//                        deps.flatMap { it.artifacts.entries }
//                            .groupBy { it.key }
//                            .forEach { (target, artifacts) ->
//                                SourceSet(target).kotlin(type)
//                                    .dependencies { this[confType] = artifacts.mapNotNull { it.value } }
//                            }
//                    }
//                else -> kotlinSourceSet.dependencies {
//                    configurations.forEach { (confType, deps) ->
//                        //                        if (sourceSet.targets.size > 1 &&
////                            deps.flatMap { it.artifacts.keys }.distinctBy { it.type }.size > 1
////                        ) error("Mixed dependencies for different platforms")
//                        this[confType] = deps.flatMap { it.artifacts.values.filterNotNull() }
//                    }
//                }
//            }
//        }
//        dependentSources.forEach(Sources::configure)
//    }
//
//    mainSources.configure()
//}

operator fun KotlinDependencyHandler.set(
    type: DependenciesConfigurationType,
    dependencies: Iterable<Artifact<out Target>>
) {
    val helper = helper()
    val handler = when (type) {
        implementation -> helper.implementation
        api -> helper.api
        runtimeOnly -> helper.runtimeOnly
        compileOnly -> helper.compileOnly
    }
    dependencies.forEach { handler.artifact(it) }
}

val test: KotlinMultiplatformExtension.() -> Unit = {
    val linux = LinuxX64.copy(name = "linux")
    val kotlind = Dependency("kotlin") {
        val common = MavenArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        Common use common.copy(postfix = "common")
        Jvm use common.copy(postfix = "jdk8")
        Js use common.copy(postfix = "js")
        ignore(linux)
    }

    val testd = Dependency("test") {
        val common = MavenArtifact("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        Common use common.copy(postfix = "common")
        Jvm use common
        Js use common.copy(postfix = "js")
        ignore(linux)
    }
    val k = MavenArtifact<JvmBasedTarget>("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")

    common {
        main {
            implementation(kotlind)
        }
        test {
            implementation {
                +testd
                +Dependency(
                    Common,
                    MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
                )
            }
        }
    }

    sourceSets {
        Android {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }

        val s = (Jvm + Android).sourceSet("jvm6")
        s {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }
        Android {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }
        (Jvm + Js) {
            main {

            }
        }
    }

//    sources(Jvm + Js + linux) {
//        dependencies {
//            main {
//                implementation(kotlind)
//            }
//            test {
//                implementation {
//                    +testd
//                    +Dependency(
//                        Common,
//                        MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
//                    )
//                }
//            }
//        }
//        Jvm.sources {
//            dependencies {
//                test {
//                    implementation(
//                        Dependency(
//                            Jvm,
//                            MavenArtifact("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31")
//                        )
//                    )
//                }
//            }
//        }
//    }
}