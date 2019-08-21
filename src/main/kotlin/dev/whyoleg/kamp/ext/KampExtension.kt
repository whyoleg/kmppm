package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.configuration.*
import dev.whyoleg.kamp.packager.*
import dev.whyoleg.kamp.plugin.Plugin
import dev.whyoleg.kamp.settings.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.target.configuration.*
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.reflect.*

@KampDSL
abstract class KampExtension<KotlinExt : KotlinProjectExtension>(versions: BuiltInVersions) : KampBase(versions), MainTargets {
    protected abstract val extPlugin: Plugin
    protected abstract val extPluginClass: KClass<KotlinExt>

    internal val targetConfigurations = mutableSetOf<TargetConfiguration>()
    internal val sources = mutableListOf<Source>()
    private val extensionBlocks = mutableListOf<KotlinExt.() -> Unit>()
    private val plugins = mutableSetOf<Plugin>()
    private val packagers = mutableListOf<Packager>()
    private val settings = LanguageSettings()

    //builders

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
        packagers += PackagersBuilder(builtIn).apply(block).packagers
    }

    fun languageSettings(block: LanguageSettings.() -> Unit) {
        settings.apply(block)
    }

    //configuration

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

    internal fun configureTarget(kotlinTarget: KotlinTarget, options: TargetOptions) {
        kotlinTarget.compilations.all {
            it.kotlinOptions {
                allWarningsAsErrors = settings.allWarningsAsErrors
                suppressWarnings = settings.suppressWarnings
                freeCompilerArgs = settings.allCompilerArguments.map { "-X$it" }

                //TODO generalize configuration
                when {
                    this is KotlinJvmOptions && options is JvmTargetOptions -> {
                        options.configure(this)
                        kotlinTarget.project.tasks.withType(KotlinCompile::class.java) {
                            it.sourceCompatibility = options.sourceCompatibility
                            it.targetCompatibility = options.targetCompatibility
                        }
                    }
                    this is KotlinJsOptions && options is JsTargetOptions   -> options.configure(this)
                    else                                                    -> error("")
                }
            }
        }
    }

    private fun configureDependencyProviders(project: Project) {
        val repositories = project.rootProject.subprojects.map(Project::getRepositories)

        sources
            .flatMap(Source::sourceSets)
            .map(SourceSet::configuration)
            .flatMap(SourceSetConfiguration::dependencyConfigurations)
            .flatMap(DependencyConfiguration::dependencies)
            .filterIsInstance<PackageDependency>()
            .map(PackageDependency::raw)
            .mapNotNull(RawDependency::provider)
            .toSet()
            .forEach { provider -> repositories.forEach { provider(it) } }
    }

    protected abstract fun sourceTypeTargets(ext: KotlinExt, sourceType: SourceSetType): Map<Target, KotlinSourceSet>

    protected abstract fun createSourceSet(ext: KotlinExt, multiTarget: MultiTarget<*>, sourceSetType: SourceSetType): KotlinSourceSet

    private fun configureSources(ext: KotlinExt, project: Project) {
        distinctSources()
            .flatMap { createSourceSets(it, ext) }
            .forEach { (mainSourceSet, targetSourceSets, list) ->
                configureLanguageSettings(targetSourceSets.values)
                list.forEach { (type, dependencies) ->
                    //println()
                    //println(type.name.capitalize())

                    val modules = dependencies.filterIsInstance<ModuleDependency>()
                    val libraries = dependencies.filterIsInstance<LibraryDependency>()
                    mainSourceSet.dependencies {
                        //println("Try modules: ${modules.joinToString(",", "[", "]")}")
                        modules(type, modules)

                        //println("Try libraries: ${libraries.joinToString(",", "[", "]")}")
                        libraries(type, libraries, project)
                    }

                    val packages = dependencies.filterIsInstance<PackageDependency>()
                    targetSourceSets.forEach { (target, sourceSet) ->
                        //println("Try $target with packages: ${packages.joinToString(",", "[", "]")}")
                        sourceSet.dependencies { packages(type, packages, target) }
                    }
                }
            }
    }

    private fun createSourceSets(
        source: Source,
        ext: KotlinExt
    ): List<Triple<KotlinSourceSet, Map<Target, KotlinSourceSet>, List<DependencyConfiguration>>> {
        val (multiTarget, configurations) = source
        val isMeta = multiTarget.targets.singleOrNull() is MetaTarget

        //println("Configure $multiTarget")

        return configurations.map { (sourceSetType, config) ->
            val (srcFolders, resFolders, list) = config
            //println("SourceSet: ${multiTarget.name}${sourceSetType.name.capitalize()}")
            val triple = if (isMeta) {
                val targetSourceSets = sourceTypeTargets(ext, sourceSetType)
                val sourceSet = targetSourceSets[Target.common]!!
                Triple(sourceSet, targetSourceSets, list)
            } else {
                val sourceSet = createSourceSet(ext, multiTarget, sourceSetType)
                val targetSourceSets = multiTarget.targets.associateWith { sourceSet }
                Triple(sourceSet, targetSourceSets, list)
            }
            triple.first.run {
                kotlin.srcDirs(*srcFolders.toTypedArray())
                //println(kotlin.srcDirs)
                resources.srcDirs(*resFolders.toTypedArray())
                //println(resources.srcDirs)
            }
            triple
        }
    }

    private fun distinctSources(): List<Source> =
        sources
            .groupBy { it.multiTarget.name }
            .mapValues { (name, sources) ->
                require(sources.map { it.multiTarget.targetCls }.toSet().size == 1)
                Source(
                    MultiTarget(name, sources.first().multiTarget.targetCls, sources.flatMap { it.multiTarget.targets }.toSet()),
                    sources.flatMap(Source::sourceSets)
                )
            }.values.toList()


    private fun configureLanguageSettings(sourceSets: Iterable<KotlinSourceSet>) {
        sourceSets.forEach {
            it.languageSettings.run {
                languageVersion = settings.languageVersion
                apiVersion = settings.apiVersion
                progressiveMode = settings.progressiveMode
                settings.allLanguageFeatures.forEach(this::enableLanguageFeature)
                settings.allExperimentalAnnotations.forEach(this::useExperimentalAnnotation)
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
