package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import kotlin.reflect.*

data class MultiDependency(
    override val raw: RawDependency,
    override val targets: Set<TargetWithPostfix<*>>
) : LibraryDependency(raw, targets)


operator fun RawDependency.invoke(targets: Set<TargetWithPostfix<*>>): MultiDependency =
    MultiDependency(this, targets)

operator fun RawDependency.invoke(vararg targets: TargetWithPostfix<*>): MultiDependency =
    invoke(targets.toSet())

operator fun MultiDependency.invoke(name: String): MultiDependency =
    copy(raw = raw.copy(name = name))

operator fun MultiDependency.getValue(thisRef: Group, property: KProperty<*>): MultiDependency =
    this(property.name.toSnakeCase())
