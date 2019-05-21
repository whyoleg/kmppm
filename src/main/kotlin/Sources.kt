package dev.whyoleg.kmppm

import dev.whyoleg.kmppm.base.*
import dev.whyoleg.kmppm.base.DependenciesConfigurationType.*
import dev.whyoleg.kmppm.base.SourceType.main
import dev.whyoleg.kmppm.base.SourceType.test
import dev.whyoleg.kmppm.base.Target
import dev.whyoleg.kmppm.base.Target.Companion.android
import dev.whyoleg.kmppm.base.Target.Companion.common
import dev.whyoleg.kmppm.base.Target.Companion.js
import dev.whyoleg.kmppm.base.Target.Companion.jvm
import dev.whyoleg.kmppm.base.Target.Companion.linuxX64
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

@DslMarker
annotation class MagicDSL

@MagicDSL
fun KMPPMExtension.targets(targets: Iterable<Target>): Unit = targets.forEach { it.configure(kotlin, it) }

@MagicDSL
fun KMPPMExtension.targets(vararg targets: Target): Unit = targets(targets.toList())

@MagicDSL
fun KMPPMExtension.common(builder: SourceConfigurationBuilder.() -> Unit) {
    val configurations = SourceConfigurationBuilder().apply(builder).data()

    val targets = configurations
        .flatMap { it.dependencyConfigurations }
        .flatMap { it.dependencies }
        .flatMap { it.artifacts.keys }
        .distinct()

    val unresolvedTargets =
        targets.filter { it.name != common.name }.distinct().mapNotNull { target ->
            target.runCatching { kotlin.targets[name] }.fold({ null }, { target })
        }.sortedBy { it.name }
    if (unresolvedTargets.isNotEmpty()) error(
        "Unresolved targets: ${unresolvedTargets.joinToString(
            "\n",
            "[\n",
            "\n]"
        )}"
    )

    configurations.forEach { (sourceType, list) ->
        val map = targets.associateWith {
            kotlin.sourceSets.maybeCreate(it.name + sourceType.name.capitalize())
        }
        list.forEach { (type, dependencies) ->
            dependencies
                .flatMap { it.artifacts.entries }
                .groupBy { it.key }
                .mapValues { it.value.mapNotNull { it.value } }
                .forEach { (target, deps) ->
                    (map[target] ?: error("No sourceSet for target $target")).dependencies { this[type] = deps }
                }
        }
    }
}

@MagicDSL
fun KMPPMExtension.sourceSets(builder: SourcesBuilder.() -> Unit) {

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

val test: KMPPMExtension.() -> Unit = {
    val linux = linuxX64.copy(name = "linux")
    val kotlind = Dependency("kotlin") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvm use cmn.copy(postfix = "jdk8")
        js use cmn.copy(postfix = "js")
        ignore(linux)
    }

    val testd = Dependency("test") {
        val cmn = MavenArtifact("org.jetbrains.kotlin", "kotlin-test", "1.3.31")
        common use cmn.copy(postfix = "common")
        jvm use cmn
        js use cmn.copy(postfix = "js")
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
                    common,
                    MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
                )
            }
        }
    }

    sourceSets {
        android {
            main {
                DependenciesConfigurationType.implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }

        val s = (jvm + android).sourceSet("jvm6")
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
        android {
            main {
                implementation(k)
            }
            test {
                api {
                    +k
                }
            }
        }
        (jvm + js) {
            main {

            }
        }
    }

//    sources(jvm + js + linux) {
//        dependencies {
//            main {
//                implementation(kotlind)
//            }
//            test {
//                implementation {
//                    +testd
//                    +Dependency(
//                        common,
//                        MavenArtifact("org.jetbrains.kotlin", "kotlin-test-annotations-common", "1.3.31")
//                    )
//                }
//            }
//        }
//        jvm.sources {
//            dependencies {
//                test {
//                    implementation(
//                        Dependency(
//                            jvm,
//                            MavenArtifact("org.jetbrains.kotlin", "kotlin-test-junit", "1.3.31")
//                        )
//                    )
//                }
//            }
//        }
//    }
}