package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampExtension<KotlinExt : KotlinProjectExtension>(private val ext: KotlinExt) : MainTargets {
    protected open val targets: MutableSet<PlatformTarget> = mutableSetOf()

    internal val sources = mutableListOf<Source>()
    private val blocks = mutableListOf<KotlinExt.() -> Unit>()

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

    private fun configureSources() {
        sources
            .groupBy { it.targetSet.name }
            .mapValues { (name, sources) ->
                require(sources.map { it.targetSet.targetCls }.toSet().size == 1)
                Source(
                    MultiTarget(
                        name,
                        sources.first().targetSet.targetCls,
                        sources.flatMap { it.targetSet.targets }.toSet()
                    ),
                    sources.flatMap { it.configurations }
                )
            }
            .values
            .forEach { (multiTarget, configurations) ->
                val isMeta = multiTarget.targets.singleOrNull() is MetaTarget
                println("Configure $multiTarget")
                configurations.forEach { (sourceType, list) ->
                    println("SourceSet: ${multiTarget.name}${sourceType.name.capitalize()}")

                    val (sourceSet, targetSourceSets) = if (isMeta) {
                        val targetSourceSets = sourceTypeTargets(sourceType)
                        val sourceSet = targetSourceSets[Target.common]
                        sourceSet to targetSourceSets
                    } else {
                        val sourceSet = ext.sourceSets.maybeCreate(multiTarget.name + sourceType.name.capitalize())
                        val targetSourceSets = multiTarget.targets.associateWith { sourceSet }
                        sourceSet to targetSourceSets
                    }
                    list.forEach { (type, dependencies) ->
                        println(type.name.capitalize())
                        val modules = dependencies.filterIsInstance<ModuleDependency>()
                        println("Try modules: ${modules.joinToString(",", "[", "]")}")
                        sourceSet?.dependencies { modules(type, modules) }

                        val libraries = dependencies.filterIsInstance<LibraryDependency>()
                        targetSourceSets.forEach { (target, sourceSet) ->
                            println("Try $target with libraries: ${libraries.joinToString(",", "[", "]")}")
                            sourceSet.dependencies { libraries(type, target, libraries) }
                        }
                        println()
                    }
                }
            }
    }
}
