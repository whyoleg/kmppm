package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.target.Target

internal data class SourceSetConfiguration(val type: SourceType, val dependencies: List<DependencySet>)

@KampDSL
class SourceSetConfigurationBuilder<T : Target>(private val targets: Set<T>) {
    //fast accessors
    val main get() = SourceType.main
    val test get() = SourceType.test

    private val sources = mutableMapOf<SourceType, MutableList<DependencySet>>()

    private fun SourceType.list(): MutableList<DependencySet> =
        sources.getOrPut(this) { mutableListOf() }

    operator fun SourceType.invoke(builder: DependencySetBuilder<T>.() -> Unit) {
        list() += DependencySetBuilder(targets).apply(builder).data()
    }

    @PublishedApi
    internal fun data(): List<SourceSetConfiguration> =
        sources.map { (type, list) -> SourceSetConfiguration(type, list) }

}