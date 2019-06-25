package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.configuration.*
import dev.whyoleg.kamp.packaging.*
import dev.whyoleg.kamp.plugin.Plugin
import dev.whyoleg.kamp.settings.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
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

    protected val targets = mutableSetOf<PlatformTarget>()


    internal val sources = mutableListOf<Source>()
    private val extensionBlocks = mutableListOf<KotlinExt.() -> Unit>()
    private val plugins = mutableSetOf<Plugin>()
    private val packagers = mutableListOf<Packager>()
    private val settings = SettingsBuilder()

    fun ext(block: KotlinExt.() -> Unit) {
        extensionBlocks += block
    }

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun packagers(block: PackagersBuilder.() -> Unit) {
        packagers += PackagersBuilder().apply(block).packagers
    }

    fun languageSettings(block: SettingsBuilder.() -> Unit) {
        settings.apply(block)
    }

    internal fun configure(project: Project) {
        project.apply { it.plugin(extPlugin.name) }
        project.extensions.configure(extPluginClass.java) { configure(it, project) }
    }

    private fun configure(ext: KotlinExt, project: Project) {
        configurePlugins(project)
        configureTargets(ext)
        configureDependencyProviders(project)
        configureSources(ext, project)
        configurePackagers(project)
        configureKotlin(ext)
    }

    private fun configurePlugins(project: Project) {
        val allPlugins = (plugins + packagers.flatMap(Packager::plugins)).toSet()
        project.repositories.apply { allPlugins.forEach { it.classpath?.provider?.invoke(this) } }
        project.apply { allPlugins.forEach { plugin -> it.plugin(plugin.name) } }
    }

    protected abstract fun configureTargets(ext: KotlinExt)

    private fun configureDependencyProviders(project: Project) {
        val repositories = project.repositories
        sources
            .flatMap(Source::sourceSets)
            .flatMap(SourceSet::dependencyConfigurations)
            .flatMap(DependencyConfiguration::dependencies)
            .filterIsInstance<PackageDependency>()
            .map(PackageDependency::raw)
            .mapNotNull(RawDependency::provider)
            .toSet()
            .forEach { it(repositories) }
    }

    protected abstract fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceSetType): Map<Target, KotlinSourceSet>

    private fun configureSources(ext: KotlinExt, project: Project) {
        distinctSources()
            .flatMap { createSourceSets(it, ext) }
            .forEach { (mainSourceSet, targetSourceSets, list) ->
                configureLanguageSettings(targetSourceSets.values)
                list.forEach { (type, dependencies) ->
                    println()
                    println(type.name.capitalize())

                    val modules = dependencies.filterIsInstance<ModuleDependency>()
                    val libraries = dependencies.filterIsInstance<LibraryDependency>()
                    mainSourceSet.dependencies {
                        println("Try modules: ${modules.joinToString(",", "[", "]")}")
                        modules(type, modules)

                        println("Try libraries: ${libraries.joinToString(",", "[", "]")}")
                        libraries(type, libraries, project)
                    }

                    val packages = dependencies.filterIsInstance<PackageDependency>()
                    targetSourceSets.forEach { (target, sourceSet) ->
                        println("Try $target with packages: ${packages.joinToString(",", "[", "]")}")
                        sourceSet.dependencies { packages(type, packages, target) }
                    }
                }
            }
    }

    private fun createSourceSets(
        source: Source,
        ext: KotlinExt
    ): List<Triple<KotlinSourceSet, Map<Target, KotlinSourceSet>, List<DependencyConfiguration>>> {
        val (multiTarget, options, configurations) = source
        val isMeta = multiTarget.targets.singleOrNull() is MetaTarget

        println("Configure $multiTarget")

        return configurations.map { (sourceSetType, list) ->
            println("SourceSet: ${multiTarget.name}${sourceSetType.name.capitalize()}")
            if (isMeta) {
                val targetSourceSets = sourceTypeTargets(ext, sourceSetType)
                val sourceSet = targetSourceSets[Target.common]!!
                Triple(sourceSet, targetSourceSets, list)
            } else {
                val sourceSet = ext.sourceSets.maybeCreate(multiTarget.name + sourceSetType.name.capitalize())
                val targetSourceSets = multiTarget.targets.associateWith { sourceSet }
                Triple(sourceSet, targetSourceSets, list)
            }
        }
    }

    private fun distinctSources(): List<Source> =
        sources
            .groupBy { it.multiTarget.name }
            .mapValues { (name, sources) ->
                require(sources.map { it.multiTarget.targetCls }.toSet().size == 1)
                Source(
                    MultiTarget(name, sources.first().multiTarget.targetCls, sources.flatMap { it.multiTarget.targets }.toSet()),
                    sources.first().kotlinOption, //TODO merge options
                    sources.flatMap(Source::sourceSets)
                )
            }.values.toList()


    private fun configureLanguageSettings(sourceSets: Iterable<KotlinSourceSet>) {
        sourceSets.forEach {
            it.languageSettings.run {
                languageVersion = settings.languageVersion
                apiVersion = settings.apiVersion
                progressiveMode = settings.progressiveMode
                settings.allFeatures.forEach(this::enableLanguageFeature)
                settings.allAnnotations.forEach(this::useExperimentalAnnotation)
            }
        }
    }

    private fun configurePackagers(project: Project) {
        packagers.forEach { it.run { project.configure() } }
    }

    private fun configureKotlin(ext: KotlinExt) {
        extensionBlocks.forEach { ext.it() }
    }

}
