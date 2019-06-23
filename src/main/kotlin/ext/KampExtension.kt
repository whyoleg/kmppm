package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.module.*
import dev.whyoleg.kamp.plugin.Plugin
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import kotlin.reflect.*

@KampDSL
abstract class KampExtension<KotlinExt : KotlinProjectExtension> : MainTargets {
    abstract val extPlugin: Plugin
    abstract val extPluginClass: KClass<KotlinExt>

    protected val targets: MutableSet<PlatformTarget> = mutableSetOf()

    internal val sources = mutableListOf<Source>()
    private val blocks = mutableListOf<KotlinExt.() -> Unit>()
    private val plugins = mutableSetOf<Plugin>()
    private val packagings = mutableListOf<Packaging>()
    private var settings = SettingsBuilder()

    fun ext(block: KotlinExt.() -> Unit) {
        blocks += block
    }

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun packaging(block: PackagingBuilder.() -> Unit) {
        packagings += PackagingBuilder().apply(block).packaging
    }

    fun languageSettings(block: SettingsBuilder.() -> Unit) {
        settings.apply(block)
    }

    @PublishedApi
    internal fun configure(project: Project) {
        project.apply { it.plugin(extPlugin.name) }
        project.extensions.configure(extPluginClass.java) {
            configurePlugins(project)
            configureTargets(it)
            configureSources(it, project)
            configurePackaging(project)
            configureKotlin(it)
        }
    }

    protected abstract fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceType): Map<Target, KotlinSourceSet>

    protected abstract fun configureTargets(ext: KotlinExt)

    private fun configurePackaging(project: Project) {
        packagings.forEach { it.run { project.configure() } }
    }

    private fun configureKotlin(ext: KotlinExt) {
        blocks.forEach { ext.it() }
    }

    private fun configurePlugins(project: Project) {
        val allPlugins = (plugins + packagings.flatMap(Packaging::plugins)).toSet()
        project.repositories.apply { allPlugins.forEach { it.classpath?.provider?.invoke(this) } }
        project.apply { allPlugins.forEach { plugin -> it.plugin(plugin.name) } }
    }

    private fun configureSources(ext: KotlinExt, project: Project) {
        project.repositories.apply {
            sources
                .flatMap(Source::configurations)
                .flatMap(SourceSetConfiguration::dependencies)
                .flatMap(DependencySet::dependencies)
                .filterIsInstance<PackageDependency>()
                .map(PackageDependency::raw)
                .mapNotNull(RawDependency::provider)
                .toSet()
                .forEach { it(this) }
        }

        val combinedSources = sources.groupBy { it.multiTarget.name }.mapValues { (name, sources) ->
            require(sources.map { it.multiTarget.targetCls }.toSet().size == 1)
            Source(
                MultiTarget(name, sources.first().multiTarget.targetCls, sources.flatMap { it.multiTarget.targets }.toSet()),
                sources.flatMap(Source::configurations)
            )
        }.values.toList()

        combinedSources.forEach { (multiTarget, configurations) ->
            val isMeta = multiTarget.targets.singleOrNull() is MetaTarget
            println("Configure $multiTarget")
            configurations.forEach { (sourceType, list) ->
                println("SourceSet: ${multiTarget.name}${sourceType.name.capitalize()}")

                val (sourceSet, targetSourceSets) = if (isMeta) {
                    val targetSourceSets = sourceTypeTargets(ext, sourceType)
                    val sourceSet = targetSourceSets[Target.common]!!
                    sourceSet to targetSourceSets
                } else {
                    val sourceSet = ext.sourceSets.maybeCreate(multiTarget.name + sourceType.name.capitalize())
                    val targetSourceSets = multiTarget.targets.associateWith { sourceSet }
                    sourceSet to targetSourceSets
                }

                targetSourceSets.values.forEach { set ->
                    set.languageSettings.apply {
                        languageVersion = settings.languageVersion
                        apiVersion = settings.apiVersion
                        progressiveMode = settings.progressiveMode
                        settings.allFeatures.forEach(this::enableLanguageFeature)
                        settings.allAnnotations.forEach(this::useExperimentalAnnotation)
                    }
                }

                list.forEach { (type, dependencies) ->
                    println(type.name.capitalize())
                    val modules = dependencies.filterIsInstance<Module>()
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
