package dev.whyoleg.kamp.base.dependency

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.target.*

data class RawLibraryDependency(val group: Group, val name: String, val version: String?)

data class LibraryVersionDependency(val group: Group, val version: String?)

operator fun LibraryVersionDependency.invoke(name: String): RawLibraryDependency = RawLibraryDependency(group, name, version)

abstract class LibraryDependency(
    open val raw: RawLibraryDependency,
    open val targets: Set<TargetWithPostfix<*>>
) : Dependency

internal fun String.toSnakeCase() =
    asIterable().joinToString("") { if (it.isUpperCase()) "-${it.toLowerCase()}" else it.toString() }


//operator fun Dependency.plus(other: Dependency): Set<Dependency> = setOf(this, other)