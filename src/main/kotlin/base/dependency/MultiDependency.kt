package dev.whyoleg.kamp.base.dependency

import dev.whyoleg.kamp.base.*
import dev.whyoleg.kamp.base.target.*
import kotlin.reflect.*

data class MultiDependency(
    override val raw: RawLibraryDependency,
    override val targets: Set<TargetWithPostfix<*>>
) : LibraryDependency(raw, targets)


operator fun RawLibraryDependency.invoke(targets: Set<TargetWithPostfix<*>>): MultiDependency =
    MultiDependency(this, targets)

operator fun RawLibraryDependency.invoke(vararg targets: TargetWithPostfix<*>): MultiDependency =
    invoke(targets.toSet())

operator fun MultiDependency.invoke(name: String): MultiDependency =
    copy(raw = raw.copy(name = name))

operator fun MultiDependency.getValue(thisRef: Group, property: KProperty<*>): MultiDependency =
    this(property.name.toSnakeCase())
