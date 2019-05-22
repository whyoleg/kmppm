@file:Suppress("FunctionName")

package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.base.Target.Companion.android
import dev.whyoleg.kamp.base.Target.Companion.common
import dev.whyoleg.kamp.base.Target.Companion.jvm

data class Dependency(val name: String? = null, val artifacts: Map<Target, Artifact<out Target>?>)

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

    infix fun <T : Target> TargetSet<T>.use(artifact: Artifact<T>) {
        targets.forEach { artifacts[it] = artifact }
    }
}

operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)

fun main() {
    Dependency {
        val dep = MavenArtifact<JvmBasedTarget>("", "")
        val depC = MavenArtifact<CommonTarget>("", "")
        common use dep.postfix("common")
        jvm use dep
        (jvm + android) use dep
        ignore(jvm)
    }
}

