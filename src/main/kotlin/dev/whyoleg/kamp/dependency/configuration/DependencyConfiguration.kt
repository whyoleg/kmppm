package dev.whyoleg.kamp.dependency.configuration

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.Target

@PublishedApi
internal data class DependencyConfiguration(
    val type: DependencyConfigurationType,
    val dependencies: Set<Dependency>
)

@KampDSL
class DependencyConfigurationBuilder<T : Target> {
    /**
     * Fast accessors, to not use imports in gradle.kts files
     */
    val implementation get() = DependencyConfigurationType.implementation
    val api get() = DependencyConfigurationType.api
    val runtimeOnly get() = DependencyConfigurationType.runtimeOnly
    val compileOnly get() = DependencyConfigurationType.compileOnly

    private val configurations = DependencyConfigurationType.values().associate { it to mutableSetOf<Dependency>() }
    private fun DependencyConfigurationType.set(): MutableSet<Dependency> = configurations.getValue(this)

    @JvmName("invoke1")
    operator fun DependencyConfigurationType.invoke(dependencies: Set<UnTypedDependency>): Unit = set().plusAssign(dependencies)

    operator fun DependencyConfigurationType.invoke(dependencies: Set<TypedDependency<in T>>): Unit = set().plusAssign(dependencies)
    operator fun DependencyConfigurationType.invoke(vararg dependencies: UnTypedDependency): Unit = set().plusAssign(dependencies)
    operator fun DependencyConfigurationType.invoke(vararg dependencies: TypedDependency<in T>): Unit = set().plusAssign(dependencies)

    @Suppress("UNCHECKED_CAST")
    operator fun DependencyConfigurationType.invoke(closure: DependencyClosure<T>.() -> Unit) {
        set() += DependencyClosure<T>().apply(closure).dependencies
    }

    internal fun data(): List<DependencyConfiguration> = configurations.map { (type, list) -> DependencyConfiguration(type, list) }

}

@KampDSL
class DependencyClosure<T : Target> {
    internal val dependencies: MutableSet<Dependency> = mutableSetOf()

    @JvmName("unaryPlus1")
    operator fun Set<UnTypedDependency>.unaryPlus(): Unit = dependencies.plusAssign(this)

    operator fun Set<TypedDependency<in T>>.unaryPlus(): Unit = dependencies.plusAssign(this)
    operator fun UnTypedDependency.unaryPlus(): Unit = dependencies.plusAssign(this)
    operator fun TypedDependency<in T>.unaryPlus(): Unit = dependencies.plusAssign(this)
}