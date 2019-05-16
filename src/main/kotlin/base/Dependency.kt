@file:Suppress("FunctionName")

package dev.whyoleg.kmppm.base

data class Dependency(
    val name: String? = null,
    val artifacts: Map<Target, Artifact>
)

fun Dependency(
    target: Target,
    artifact: Artifact
): Dependency = Dependency(null, target, artifact)

fun Dependency(
    name: String?,
    target: Target,
    artifact: Artifact
): Dependency = Dependency(name, mapOf(target to artifact))

inline fun Dependency(
    name: String? = null,
    configuration: DependencyBuilder.() -> Unit
): Dependency = Dependency(name, DependencyBuilder().apply(configuration).artifacts)

inline class DependencyBuilder(@PublishedApi internal val artifacts: MutableMap<Target, Artifact> = mutableMapOf()) {
    infix fun Iterable<Target>.use(artifact: Artifact) {
        forEach { artifacts[it] = artifact }
    }

    infix fun Target.use(artifact: Artifact) {
        artifacts[this] = artifact
    }

    infix fun SourceSet.use(artifact: Artifact) {
        targets.forEach { artifacts[it] = artifact }
    }
}

operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)

//fun main() {
//    Dependency {
//        val dep = MavenArtifact("", "")
//        META use dep.copy(postfix = "common")
//        JVM use dep
//        (JVM + JVM + META) use dep
//        (JVM + JVM).sourceSet("jvm") use dep
//        JVM use IgnoredArtifact
//    }
//}
//
