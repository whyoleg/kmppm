package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

abstract class PackageDependency(
    open val raw: RawDependency,
    open val targets: Set<TargetWithPostfix<*>>
) : Dependency

internal fun String.toSnakeCase() =
    asIterable().joinToString("") { if (it.isUpperCase()) "-${it.toLowerCase()}" else it.toString() }
