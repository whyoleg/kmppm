package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.MetaTarget
import dev.whyoleg.kamp.base.RealTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.TargetSet
import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.builder.Source
import dev.whyoleg.kamp.builder.SourceBuilder
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@KampDSL
class KampExtension(private val ext: KotlinMultiplatformExtension) {
    val targets = mutableSetOf<RealTarget>()

    private val sources = mutableListOf<Source>()
    private val blocks = mutableListOf<KotlinMultiplatformExtension.() -> Unit>()

    fun kotlin(block: KotlinMultiplatformExtension.() -> Unit): Unit {
        blocks += block
    }

    fun sourceSets(builder: SourceBuilder.() -> Unit) {
        sources += SourceBuilder().apply(builder).sources
    }

    internal fun configure() {
        configureTargets()
        configureSources()
        configureKotlin()
    }

    private fun configureKotlin() {
        blocks.forEach { ext.it() }
    }

    private fun configureTargets() {
        (targets + Target.common).forEach { it.configure(ext, it) }
    }

    private fun configureSources() {
        sources
            .groupBy { it.targetSet.name }
            .mapValues { (name, sources) ->
                require(sources.map { it.targetSet.targetCls }.toSet().size == 1)
                Source(
                    TargetSet(
                        name,
                        sources.first().targetSet.targetCls,
                        sources.flatMap { it.targetSet.targets }.toSet()
                    ),
                    sources.flatMap { it.configurations }
                )
            }
            .values
            .forEach { (targetSet, configurations) ->
                println()
                println("Configure $targetSet")
                if (targetSet.targets.singleOrNull() is MetaTarget) {
                    println("Configure META")
                    configurations.forEach { (sourceType, list) ->
                        println()
                        println("Configure sourceSet: ${targetSet.name}${sourceType.name.capitalize()}")
                        val targetSourceSets = (targets + Target.common).associateWith {
                            ext.sourceSets.maybeCreate(it.name + sourceType.name.capitalize())
                        }
                        list.forEach { (type, dependencies) ->
                            println()
                            println("Configure dependencies $type")

                            val deps =
                                dependencies
                                    .flatMap { it.artifacts.entries }
                                    .groupBy { it.key }
                                    .mapValues { it.value.mapNotNull { it.value } }

                            targetSourceSets.forEach { (target, sourceSet) ->
                                val targetDeps = deps[target].orEmpty()
                                println("Configure $target with ${targetDeps.joinToString(",", "[", "]")}")
                                sourceSet.dependencies { this[type] = targetDeps }
                            }
                        }
                    }
                } else {
                    println("Configure SourceSet")
                    configurations.forEach { (sourceType, list) ->
                        println()
                        println("Configure sourceSet: ${targetSet.name}${sourceType.name.capitalize()}")
                        val sourceSet = ext.sourceSets.maybeCreate(targetSet.name + sourceType.name.capitalize())
                        list.forEach { (type, dependencies) ->
                            println()
                            println("Configure dependencies $type")
                            val targetDeps = dependencies.mapNotNull { (_, artifacts) ->
                                targetSet.targets.map { artifacts[it] }.requireNoNulls().toSet().singleOrNull()
                            }
                            println("Configure with ${targetDeps.joinToString(",", "[", "]")}")
                            sourceSet.dependencies { this[type] = targetDeps }
                        }
                    }
                }
            }
    }
}
