package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

@Suppress("EnumEntryName")
enum class SourceType { main, test }

data class SourceConfiguration(
    val type: SourceType,
    val dependencyConfigurations: List<DependenciesConfiguration>
)

@MagicDSL
class SourceConfigurationBuilder<T : Target>(private val targets: Set<T>) {
    private val sources = mutableMapOf<SourceType, MutableList<DependenciesConfiguration>>()

    private fun SourceType.list(): MutableList<DependenciesConfiguration> =
        sources.getOrPut(this) { mutableListOf() }

    operator fun SourceType.invoke(builder: DependenciesConfigurationBuilder<T>.() -> Unit) {
        list() += DependenciesConfigurationBuilder(targets).apply(builder).data()
    }

    fun data(): List<SourceConfiguration> =
        sources.map { (type, list) -> SourceConfiguration(type, list) }

}