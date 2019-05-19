package dev.whyoleg.kmppm.base

import dev.whyoleg.kmppm.MagicDSL

@MagicDSL
class TypedDependenciesConfigurationBuilder<T : Target>(private val targets: Set<T>) {
    private val dependencies = mutableMapOf<DependenciesConfigurationType, MutableSet<Dependency>>()

    private fun DependenciesConfigurationType.set(): MutableSet<Dependency> =
        dependencies.getOrPut(this) { mutableSetOf() }

    operator fun DependenciesConfigurationType.invoke(dependency: Dependency) {
        set() += dependency
    }

    @JvmName("invokeSetDependencies")
    operator fun DependenciesConfigurationType.invoke(dependencies: Set<Dependency>) {
        set() += dependencies
    }

    operator fun DependenciesConfigurationType.invoke(vararg dependencies: Dependency) {
        set() += dependencies
    }

    fun Dependency(artifact: Artifact<in T>): Dependency = Dependency { targets.forEach { it use artifact } }

    operator fun DependenciesConfigurationType.invoke(artifact: Artifact<in T>) {
        set() += Dependency(artifact)
    }

    operator fun DependenciesConfigurationType.invoke(dependencies: Set<Artifact<in T>>) {
        set() += dependencies.map { Dependency(it) }
    }

    operator fun DependenciesConfigurationType.invoke(vararg dependencies: Artifact<in T>) {
        set() += dependencies.map { Dependency(it) }
    }

    @Suppress("UNCHECKED_CAST")
    operator fun DependenciesConfigurationType.invoke(closure: TypedDependencyClosure<T>.() -> Unit) {
        set() += TypedDependencyClosure<T>().apply(closure).dependencies.mapNotNull {
            when (it) {
                is Dependency -> it
                is Artifact<*> -> Dependency(it as Artifact<in T>)
                else -> null
            }
        }
    }

    fun data(): List<DependenciesConfiguration> =
        dependencies.map { (type, list) -> DependenciesConfiguration(type, list) }

}

@MagicDSL
inline class TypedDependencyClosure<T : Target>(internal val dependencies: MutableSet<Any> = mutableSetOf()) {
    operator fun Artifact<in T>.unaryPlus() {
        dependencies += this
    }

    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}