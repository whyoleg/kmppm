package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.base.MetaTarget
import dev.whyoleg.kamp.base.PlatformTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.TargetSet
import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.builder.Source
import dev.whyoleg.kamp.builder.SourceType
import dev.whyoleg.kamp.set
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@KampDSL
abstract class KampExtension<KotlinExt : KotlinProjectExtension>(private val ext: KotlinExt) : Target.WithTargets {
    protected open val targets: MutableSet<PlatformTarget> = mutableSetOf()

    internal val sources = mutableListOf<Source>()
    protected val blocks = mutableListOf<KotlinExt.() -> Unit>()

    open fun kotlin(block: KotlinExt.() -> Unit): Unit {
        blocks += block
    }

    @PublishedApi
    internal fun configure() {
        configureTargets()
        configureSources()
        configureKotlin()
    }

    private fun configureKotlin() {
        blocks.forEach { ext.it() }
    }

    protected abstract fun configureTargets()

    protected abstract fun sourceTypeTargets(sourceType: SourceType): Map<Target, KotlinSourceSet>

    fun configureSources() {
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
                        val targetSourceSets = sourceTypeTargets(sourceType)
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
