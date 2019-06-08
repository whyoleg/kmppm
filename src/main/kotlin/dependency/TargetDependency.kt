package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import kotlin.reflect.*

data class TargetDependency<T : Target>(
    override val raw: RawDependency,
    override val targets: Set<TargetWithPostfix<T>>
) : PackageDependency(raw, targets)


operator fun <T : Target> RawDependency.invoke(vararg targets: T): TargetDependency<T> =
    TargetDependency(this, targets.map { it.invoke() }.toSet())

operator fun <T : Target> RawDependency.invoke(target: TargetWithPostfix<T>): TargetDependency<T> =
    TargetDependency(this, setOf(target))

operator fun <T : Target> RawDependency.invoke(target: T): TargetDependency<T> = this(target())

operator fun <T : Target> TargetDependency<T>.invoke(name: String): TargetDependency<T> =
    copy(raw = raw.copy(name = name))

operator fun <T : Target> TargetDependency<T>.getValue(thisRef: Group, property: KProperty<*>): TargetDependency<T> =
    this(property.name.toSnakeCase())
