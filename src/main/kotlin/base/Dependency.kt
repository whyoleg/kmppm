@file:Suppress("FunctionName")

package dev.whyoleg.kamp.base

data class Dependency(val name: String? = null, val artifacts: Map<Target, Artifact<out Target>?>)

fun Dependency.copy(name: String? = this.name, configuration: DependencyBuilder.(old: Dependency) -> Unit): Dependency =
    Dependency(name, artifacts + DependencyBuilder().apply { configuration(this@copy) }.artifacts)

operator fun <T : Target> Dependency.get(target: T): Artifact<T>? = artifacts[target] as Artifact<T>?

fun <T : Target> Dependency(target: T, artifact: Artifact<T>): Dependency =
    Dependency(null, target, artifact)

fun <T : Target> Dependency(name: String?, target: T, artifact: Artifact<T>): Dependency =
    Dependency(name, mapOf(target to artifact))

inline fun Dependency(name: String? = null, configuration: DependencyBuilder.() -> Unit): Dependency =
    Dependency(name, DependencyBuilder().apply(configuration).artifacts)

inline class DependencyBuilder(@PublishedApi internal val artifacts: MutableMap<Target, Artifact<out Target>?> = mutableMapOf()) {
    infix fun <T : Target> Iterable<T>.use(artifact: Artifact<T>) {
        forEach { artifacts[it] = artifact }
    }

    infix fun <T : Target> T.use(artifact: Artifact<T>) {
        artifacts[this] = artifact
    }

    fun ignore(vararg targets: Target) {
        targets.forEach { artifacts[it] = null }
    }

    infix fun <T : PlatformTarget> TargetSet<T>.use(artifact: Artifact<T>) {
        targets.forEach { artifacts[it] = artifact }
    }
}

operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)