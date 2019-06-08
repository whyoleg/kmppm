package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

abstract class PackageDependency(
    open val raw: RawDependency,
    open val targets: Set<TargetWithPostfix<*>>
) : Dependency {
    operator fun component1() = raw
    operator fun component2() = targets
}

internal fun String.toSnakeCase() =
    asIterable().joinToString("") { if (it.isUpperCase()) "-${it.toLowerCase()}" else it.toString() }
