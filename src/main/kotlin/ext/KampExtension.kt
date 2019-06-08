package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.Plugin
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampExtension<KotlinExt : KotlinProjectExtension>(private val ext: KotlinExt, private val project: Project) : MainTargets {
    protected open val targets: MutableSet<PlatformTarget> = mutableSetOf()

    internal val sources = mutableListOf<Source>()
    private val blocks = mutableListOf<KotlinExt.() -> Unit>()
    private val plugins = mutableSetOf<Plugin>()

    open fun kotlin(block: KotlinExt.() -> Unit): Unit {
        blocks += block
    }

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun packaging(block: Packaging.() -> Unit) {

    }

    @PublishedApi
    internal fun configure() {
        configureTargets()
        configureSources()
        configureKotlin()
        configurePlugins()
    }

    protected abstract fun sourceTypeTargets(sourceType: SourceType): Map<Target, KotlinSourceSet>

    protected abstract fun configureTargets()

    private fun configureKotlin() {
        blocks.forEach { ext.it() }
    }

    private fun configurePlugins() {
        project.apply {
            plugins.forEach { plugin ->
                it.plugin(plugin.name)
            }
        }
    }

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
                        val sourceSet = targetSourceSets[Target.common]!!
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
                        sourceSet.dependencies { modules(type, modules) }

                        val jars = dependencies.filterIsInstance<LibraryDependency>()
                        println("Try jars: ${jars.joinToString(",", "[", "]")}")
                        sourceSet.dependencies { libraries(type, jars, project) }

                        val libraries = dependencies.filterIsInstance<PackageDependency>()
                        targetSourceSets.forEach { (target, sourceSet) ->
                            println("Try $target with libraries: ${libraries.joinToString(",", "[", "]")}")
                            sourceSet.dependencies { packages(type, libraries, target) }

                        }
                        println()
                    }
                }
            }
    }
}
