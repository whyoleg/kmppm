package dev.whyoleg.kmppm.base

import dev.whyoleg.kmppm.DependencyHandler

sealed class Artifact<T : Target>

data class MavenArtifact<T : Target>(
    val group: String,
    val artifact: String,
    val version: String? = null,
    val postfix: String? = null
) : Artifact<T>()

fun <T : Target, R : Target> MavenArtifact<T>.t(): MavenArtifact<R> = MavenArtifact(group, artifact, version, postfix)

fun <T : Target, R : Target> MavenArtifact<T>.postfix(postfix: String?): MavenArtifact<R> =
    copy(postfix = postfix).t()

data class ModuleArtifact<T : Target>(
    val name: String,
    val configuration: String? = null
) : Artifact<T>()

fun DependencyHandler.artifact(art: Artifact<*>): Unit = with(art) {
    println("Add: $art")
    when (this) {
        is MavenArtifact<*> -> add("$group:$artifact${postfix?.let { "-$it" } ?: ""}${version?.let { ":$it" } ?: ""}")
        is ModuleArtifact<*> -> add(project(name, configuration))
    }
}