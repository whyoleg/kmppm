package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

@MagicDSL
class TypedSourceConfigurationBuilder<T : Target>(private val targets: Set<T>) {
    private val sources = mutableMapOf<SourceType, MutableList<DependenciesConfiguration>>()

    private fun SourceType.list(): MutableList<DependenciesConfiguration> =
        sources.getOrPut(this) { mutableListOf() }

    operator fun SourceType.invoke(configuration: DependenciesConfiguration) {
        list() += configuration
    }

    @JvmName("inv2")
    operator fun SourceType.invoke(builder: TypedDependenciesConfigurationBuilder<T>.() -> Unit) {
        list() += TypedDependenciesConfigurationBuilder(targets).apply(builder).data()
    }

    fun data(): List<SourceConfiguration> =
        sources.map { (type, list) -> SourceConfiguration(type, list) }

}