package dev.whyoleg.kmppm

import dev.whyoleg.kmppm.Target.Companion.JVM
import dev.whyoleg.kmppm.Target.Companion.META

data class Dependency(
    val name: String,
    val providers: Map<NamedTarget, DependencyProvider>
)

@Suppress("FunctionName")
fun Dependency(name: String, configuration: DependencyBuilder.() -> Unit): Dependency =
    Dependency(name, DependencyBuilder().apply(configuration).providers)

operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)

class DependencyBuilder {
    internal val providers = mutableMapOf<NamedTarget, DependencyProvider>()

    infix fun Iterable<NamedTarget>.with(dependencyProvider: DependencyProvider) {
        forEach { providers[it] = dependencyProvider }
    }

    infix fun NamedTarget.with(dependencyProvider: DependencyProvider) {
        targets() with dependencyProvider
    }
}

fun main() {
    Dependency("") {
        val dep = Maven("", "")
        META with dep.copy(postfix = "common")
        JVM with dep
        (JVM + JVM + META) with dep

        JVM with Ignored
    }
}