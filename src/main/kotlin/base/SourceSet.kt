@file:Suppress("FunctionName")

package dev.whyoleg.kmppm.base

data class SourceSet<T : Target>(val name: String? = null, val targets: Set<T>)

fun <T : Target> SourceSet(target: T): SourceSet<T> = SourceSet(target.name, setOf(target))

fun <T : Target> Set<T>.sourceSet(name: String): SourceSet<T> = SourceSet(name, this)
