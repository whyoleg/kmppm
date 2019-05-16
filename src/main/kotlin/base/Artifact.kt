package dev.whyoleg.kmppm.base

import dev.whyoleg.kmppm.DependencyHandler

sealed class Artifact

data class MavenArtifact(
    val group: String,
    val artifact: String,
    val version: String? = null,
    val postfix: String? = null
) : Artifact()

data class ModuleArtifact(
    val name: String,
    val configuration: String? = null
) : Artifact()

object IgnoredArtifact : Artifact()

fun DependencyHandler.artifact(art: Artifact): Unit = with(art) {
    println("Add: $art")
    when (this) {
        is MavenArtifact -> add("$group:$artifact${postfix?.let { "-$it" } ?: ""}${version?.let { ":$it" } ?: ""}")
        is ModuleArtifact -> add(project(name, configuration))
    }
}