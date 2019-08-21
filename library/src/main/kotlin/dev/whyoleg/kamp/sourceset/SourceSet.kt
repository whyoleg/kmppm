package dev.whyoleg.kamp.sourceset

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.configuration.*
import dev.whyoleg.kamp.target.Target

internal data class SourceSet(
    val type: SourceSetType,
    val configuration: SourceSetConfiguration
)

internal data class SourceSetConfiguration(
    val srcFolders: List<String>,
    val resFolders: List<String>,
    val dependencyConfigurations: List<DependencyConfiguration>
)

@KampDSL
class SourceSetBuilder<T : Target> {
    //fast accessors
    val main get() = SourceSetType.main
    val test get() = SourceSetType.test

    private val sourceSets = SourceSetType.values().associate { it to mutableListOf<SourceSetConfiguration>() }
    private fun SourceSetType.list(): MutableList<SourceSetConfiguration> = sourceSets.getValue(this)

    operator fun SourceSetType.invoke(
        src: List<String> = emptyList(),
        res: List<String> = emptyList(),
        builder: DependencyConfigurationBuilder<T>.() -> Unit
    ) {
        list() += SourceSetConfiguration(src, res, DependencyConfigurationBuilder<T>().apply(builder).data())
    }

    @PublishedApi
    internal fun data(): List<SourceSet> = sourceSets.map { (type, list) ->
        SourceSet(
            type,
            SourceSetConfiguration(
                list.flatMap(SourceSetConfiguration::srcFolders),
                list.flatMap(SourceSetConfiguration::resFolders),
                list.flatMap(SourceSetConfiguration::dependencyConfigurations)
            )
        )
    }

}