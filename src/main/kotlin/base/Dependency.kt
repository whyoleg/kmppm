@file:Suppress("FunctionName")

package dev.whyoleg.kmppm.base

import dev.whyoleg.kmppm.base.Target.Companion.Android
import dev.whyoleg.kmppm.base.Target.Companion.Common
import dev.whyoleg.kmppm.base.Target.Companion.Jvm

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

    infix fun <T : Target> SourceSet<T>.use(artifact: Artifact<T>) {
        targets.forEach { artifacts[it] = artifact }
    }
}

operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)

fun main() {
    Dependency {
        val dep = MavenArtifact<JvmBasedTarget>("", "")
        val depC = MavenArtifact<CommonTarget>("", "")
        Common use dep.postfix("common")
        Jvm use dep
        (Jvm + Android) use dep
        ignore(Jvm)
    }
}

