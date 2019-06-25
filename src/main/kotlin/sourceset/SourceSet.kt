package dev.whyoleg.kamp.sourceset

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.configuration.*
import dev.whyoleg.kamp.settings.*
import dev.whyoleg.kamp.target.Target

internal data class SourceSet(
    val type: SourceSetType,
    val dependencyConfigurations: List<DependencyConfiguration>
)

@PublishedApi
internal data class SourceData<T : Target>(
    val kotlinOptions: KotlinOptionsBuilder<T>,
    val sourceSets: List<SourceSet>
)

@KampDSL
class SourceSetBuilder<T : Target> {
    //fast accessors
    val main get() = SourceSetType.main
    val test get() = SourceSetType.test

    private val sourceSets = SourceSetType.values().associate { it to mutableListOf<DependencyConfiguration>() }
    private fun SourceSetType.list(): MutableList<DependencyConfiguration> = sourceSets.getValue(this)

    private val kotlinOptions = KotlinOptionsBuilder<T>()

    operator fun SourceSetType.invoke(
        sources: List<String> = emptyList(),
        resources: List<String> = emptyList(),
        builder: DependencyConfigurationBuilder<T>.() -> Unit
    ) {
        list() += DependencyConfigurationBuilder<T>().apply(builder).data()
    }

    fun kotlinOptions(block: KotlinOptionsBuilder<T>.() -> Unit) {
        kotlinOptions.apply(block)
    }

    @PublishedApi
    internal fun data(): SourceData<T> = SourceData(kotlinOptions, sourceSets.map { (type, list) -> SourceSet(type, list) })

}