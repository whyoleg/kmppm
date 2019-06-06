package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.base.Artifact
import dev.whyoleg.kamp.base.Dependency
import dev.whyoleg.kamp.base.Target

@PublishedApi
internal data class DependencySet(val type: DependencySetType, val dependencies: Set<Dependency>)

@KampDSL
class DependencySetBuilder<T : Target>(private val targets: Set<T>) {
    //fast accessors
    val implementation get() = DependencySetType.implementation
    val api get() = DependencySetType.api
    val runtimeOnly get() = DependencySetType.runtimeOnly
    val compileOnly get() = DependencySetType.compileOnly


    private val dependencies = mutableMapOf<DependencySetType, MutableSet<Dependency>>()

    private fun DependencySetType.set(): MutableSet<Dependency> = dependencies.getOrPut(this) { mutableSetOf() }

    operator fun DependencySetType.invoke(dependency: Dependency) {
        set() += dependency
    }

    @JvmName("invokeSetDependencies")
    operator fun DependencySetType.invoke(dependencies: Set<Dependency>) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(vararg dependencies: Dependency) {
        set() += dependencies
    }

    private fun Dependency(artifact: Artifact<in T>): Dependency = Dependency { targets.forEach { it use artifact } }

    operator fun DependencySetType.invoke(artifact: Artifact<in T>) {
        set() += Dependency(artifact)
    }

    operator fun DependencySetType.invoke(dependencies: Set<Artifact<in T>>) {
        set() += dependencies.map { Dependency(it) }
    }

    operator fun DependencySetType.invoke(vararg dependencies: Artifact<in T>) {
        set() += dependencies.map { Dependency(it) }
    }

    @Suppress("UNCHECKED_CAST")
    operator fun DependencySetType.invoke(closure: DependencyClosure<T>.() -> Unit) {
        set() += DependencyClosure<T>().apply(closure).dependencies.mapNotNull {
            when (it) {
                is Dependency -> it
                is Artifact<*> -> Dependency(it as Artifact<in T>)
                else -> null
            }
        }
    }

    internal fun data(): List<DependencySet> =
        dependencies.map { (type, list) -> DependencySet(type, list) }

}

@KampDSL
inline class DependencyClosure<T : Target>(internal val dependencies: MutableSet<Any> = mutableSetOf()) {
    operator fun Artifact<in T>.unaryPlus() {
        dependencies += this
    }

    operator fun Dependency.unaryPlus() {
        dependencies += this
    }
}