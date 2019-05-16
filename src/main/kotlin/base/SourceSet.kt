@file:Suppress("FunctionName")

package dev.whyoleg.kmppm.base

data class SourceSet(val name: String? = null, val targets: Set<Target>)

fun SourceSet(target: Target): SourceSet = SourceSet(target.name, setOf(target))

fun Set<Target>.sourceSet(name: String? = null): SourceSet = SourceSet(name, this)
