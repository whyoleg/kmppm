package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

@Suppress("EnumEntryName")
enum class SourceType { main, test }

data class SourceConfiguration(
    val type: SourceType,
    val dependencyConfigurations: List<DependenciesConfiguration>
)

@MagicDSL
class SourceConfigurationBuilder {
    private val sources = mutableMapOf<SourceType, MutableList<DependenciesConfiguration>>()

    private fun SourceType.list(): MutableList<DependenciesConfiguration> =
        sources.getOrPut(this) { mutableListOf() }

    operator fun SourceType.invoke(configuration: DependenciesConfiguration) {
        list() += configuration
    }

    operator fun SourceType.invoke(builder: DependenciesConfigurationBuilder.() -> Unit) {
        list() += DependenciesConfigurationBuilder().apply(builder).data()
    }

    fun data(): List<SourceConfiguration> =
        sources.map { (type, list) -> SourceConfiguration(type, list) }
}