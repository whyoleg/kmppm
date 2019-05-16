package dev.whyoleg.kmppm.base

data class Sources(
    val sourceSet: SourceSet,
    val dependentSources: List<Sources>,
    val sourceConfigurations: List<SourceConfiguration>
)

class SourcesBuilder {
    private val dependantSources: MutableList<Sources> = mutableListOf<Sources>()
    private val sourceConfigurations = mutableListOf<SourceConfiguration>()

    fun Target.sources(build: SourcesBuilder.() -> Unit = {}) = SourceSet(this).sources(build)
    fun Target.sources(builder: SourcesBuilder) = SourceSet(this).sources(builder)

    fun Set<Target>.sources(build: SourcesBuilder.() -> Unit = {}): Unit = sourceSet().sources(build)
    fun Set<Target>.sources(builder: SourcesBuilder): Unit = sourceSet().sources(builder)

    fun SourceSet.sources(build: SourcesBuilder.() -> Unit = {}): Unit = sources(SourcesBuilder().apply(build))
    fun SourceSet.sources(builder: SourcesBuilder) {
        dependantSources += Sources(this, builder.dependantSources, builder.sourceConfigurations)
    }

    fun dependencies(builder: SourceConfigurationBuilder.() -> Unit) {
        sourceConfigurations += SourceConfigurationBuilder().apply(builder).data()
    }

    fun sources(sourceSet: SourceSet): Sources = Sources(sourceSet, dependantSources, sourceConfigurations)
}

fun Sources.targets(): Set<Target> {
    val targets = mutableSetOf<Target>()
    targets += sourceSet.targets
    targets += dependentSources.flatMap(Sources::targets)
    return targets
}