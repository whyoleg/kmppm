package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.Target

@PublishedApi
internal data class DependencySet(val type: DependencySetType, val dependencies: Set<Dependency>)

@KampDSL
class DependencySetBuilder<T : Target> {
    //fast accessors
    val implementation get() = DependencySetType.implementation
    val api get() = DependencySetType.api
    val runtimeOnly get() = DependencySetType.runtimeOnly
    val compileOnly get() = DependencySetType.compileOnly


    private val dependencies = mutableMapOf<DependencySetType, MutableSet<Dependency>>()
    private fun DependencySetType.set(): MutableSet<Dependency> = dependencies.getOrPut(this) { mutableSetOf() }

    @JvmName("invoke1")
    operator fun DependencySetType.invoke(dependencies: Set<UnTypedDependency>): Unit = set().plusAssign(dependencies)

    operator fun DependencySetType.invoke(vararg dependencies: UnTypedDependency): Unit = set().plusAssign(dependencies)
    operator fun DependencySetType.invoke(dependencies: Set<TypedDependency<in T>>): Unit = set().plusAssign(dependencies)
    operator fun DependencySetType.invoke(vararg dependencies: TypedDependency<in T>): Unit = set().plusAssign(dependencies)

    @Suppress("UNCHECKED_CAST")
    operator fun DependencySetType.invoke(closure: DependencyClosure<T>.() -> Unit) {
        set() += DependencyClosure<T>().apply(closure).dependencies
    }

    internal fun data(): List<DependencySet> = dependencies.map { (type, list) -> DependencySet(type, list) }

}

@KampDSL
class DependencyClosure<T : Target>(internal val dependencies: MutableSet<Dependency> = mutableSetOf()) {
    operator fun TypedDependency<in T>.unaryPlus(): Unit = dependencies.plusAssign(this)
    operator fun UnTypedDependency.unaryPlus(): Unit = dependencies.plusAssign(this)

    operator fun Set<TypedDependency<in T>>.unaryPlus(): Unit = dependencies.plusAssign(this)

    @JvmName("unaryPlus1")
    operator fun Set<UnTypedDependency>.unaryPlus(): Unit = dependencies.plusAssign(this)
}