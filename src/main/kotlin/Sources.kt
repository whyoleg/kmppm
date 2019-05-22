package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.DependenciesConfigurationType.*
import dev.whyoleg.kamp.base.SourceType.main
import dev.whyoleg.kamp.base.SourceType.test
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.Target.Companion.android
import dev.whyoleg.kamp.base.Target.Companion.common
import dev.whyoleg.kamp.base.Target.Companion.js
import dev.whyoleg.kamp.base.Target.Companion.jvm
import dev.whyoleg.kamp.base.Target.Companion.linuxX64
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

@DslMarker
annotation class MagicDSL

@MagicDSL
fun KampExtension.targets(targets: Iterable<Target>): Unit = targets.forEach { it.configure(kotlin, it) }

@MagicDSL
fun KampExtension.targets(vararg targets: Target): Unit = targets(targets.toList())

@MagicDSL
fun KampExtension.sourceSets(builder: SourcesBuilder.() -> Unit) {
    val sources = SourcesBuilder().apply(builder).data()

    val targets = sources
        .flatMap { it.sourceConfigurations }
        .flatMap { it.dependencyConfigurations }
        .flatMap { it.dependencies }
        .flatMap { it.artifacts.keys }
        .distinct()

    println(targets)
    println(sources.flatMap { it.targetSet.targets }.distinct())

    targets
        .filter { it.name != common.name }
        .distinct()
        .mapNotNull { t -> t.runCatching { kotlin.targets.getByName(name) }.fold({ null }, { t }) }
        .sortedBy { it.name }
        .takeIf { it.isNotEmpty() }
        ?.let { error("Unresolved targets: ${it.joinToString("\n", "[\n", "\n]")}") }

    sources
        .groupBy { it.targetSet.name }
        .mapValues { (name, sources) ->
            require(sources.map { it.targetSet.targetCls }.toSet().size == 1)
            val cls = sources.first().targetSet.targetCls
            val ts = sources.flatMap { it.targetSet.targets }.toSet()
            val cs = sources.flatMap { it.sourceConfigurations }
            Sources(TargetSet(name, cls, ts), cs)
        }
        .values
        .forEach { (targetSet, configurations) ->
            //TODO MOVE APPLY to end
            println("Configure $targetSet")
            configurations.forEach { (sourceType, list) ->
                println("Configure sourceSet: ${targetSet.name}${sourceType.name.capitalize()}")
                val map =
                    (targetSet.targets.takeUnless { it.size == 1 && it.first() == common } ?: targets).associateWith {
                        kotlin.sourceSets.maybeCreate(it.name + sourceType.name.capitalize())
                    }
                list.forEach { (type, dependencies) ->
                    println("Configure dependencies $type")
                    val deps = dependencies
                        .flatMap { it.artifacts.entries }
                        .groupBy { it.key }
                        .mapValues { it.value.mapNotNull { it.value } }

                    map.forEach { (target, sourceSet) ->
                        val targetDeps = deps[target].orEmpty()
                        println("Configure $target with ${targetDeps.joinToString(",", "[", "]")}")
                        sourceSet.dependencies { this[type] = targetDeps }
                    }
                }
            }
        }
}

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

val test: KampExtension.() -> Unit = {
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

    sourceSets {
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

//    data(jvm + js + linux) {
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
//        jvm.data {
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