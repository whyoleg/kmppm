package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.MagicDSL

@Suppress("EnumEntryName")
enum class DependenciesConfigurationType { implementation, api, runtimeOnly, compileOnly }

data class DependenciesConfiguration(
    val type: DependenciesConfigurationType,
    val dependencies: Set<Dependency>
)

@MagicDSL
class DependenciesConfigurationBuilder {
    private val dependencies = mutableMapOf<DependenciesConfigurationType, MutableSet<Dependency>>()

    private fun DependenciesConfigurationType.set(): MutableSet<Dependency> =
        dependencies.getOrPut(this) { mutableSetOf() }

    operator fun DependenciesConfigurationType.invoke(dependency: Dependency) {
        set() += dependency
    }

    operator fun DependenciesConfigurationType.invoke(dependencies: Set<Dependency>) {
        set() += dependencies
    }

    operator fun DependenciesConfigurationType.invoke(vararg dependencies: Dependency) {
        set() += dependencies
    }

    operator fun DependenciesConfigurationType.invoke(closure: DependencyClosure.() -> Unit) {
        set() += DependencyClosure().apply(closure).dependencies
    }

    fun data(): List<DependenciesConfiguration> =
        dependencies.map { (type, list) -> DependenciesConfiguration(type, list) }

}

@MagicDSL
inline class DependencyClosure(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}