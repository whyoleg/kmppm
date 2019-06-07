package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.base.dependency.*
import dev.whyoleg.kamp.base.target.Target

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

    operator fun DependencySetType.invoke(dependency: MultiDependency) {
        set() += dependency
    }

    @JvmName("invokeMulti")
    operator fun DependencySetType.invoke(dependencies: Set<MultiDependency>) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(vararg dependencies: MultiDependency) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(dependency: ModuleDependency) {
        set() += dependency
    }

    @JvmName("invokeModule")
    operator fun DependencySetType.invoke(dependencies: Set<ModuleDependency>) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(vararg dependencies: ModuleDependency) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(dependency: TargetDependency<in T>) {
        set() += dependency
    }

    @JvmName("invokeTarget")
    operator fun DependencySetType.invoke(dependencies: Set<TargetDependency<in T>>) {
        set() += dependencies
    }

    operator fun DependencySetType.invoke(vararg dependencies: TargetDependency<in T>) {
        set() += dependencies
    }

    @Suppress("UNCHECKED_CAST")
    operator fun DependencySetType.invoke(closure: DependencyClosure<T>.() -> Unit) {
        set() += DependencyClosure<T>().apply(closure).dependencies
    }

    internal fun data(): List<DependencySet> =
        dependencies.map { (type, list) -> DependencySet(type, list) }

}

@KampDSL
inline class DependencyClosure<T : Target>(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
    operator fun TargetDependency<in T>.unaryPlus() {
        dependencies += this
    }

    operator fun MultiDependency.unaryPlus() {
        dependencies += this
    }

    operator fun ModuleDependency.unaryPlus() {
        dependencies += this
    }
}