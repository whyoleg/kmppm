package dev.whyoleg.kmppm.base

@Suppress("EnumEntryName")
enum class DependenciesConfigurationType { implementation, api, runtimeOnly, compileOnly }

data class DependenciesConfiguration(
    val type: DependenciesConfigurationType,
    val dependencies: Set<Dependency>
)

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

inline class DependencyClosure(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}