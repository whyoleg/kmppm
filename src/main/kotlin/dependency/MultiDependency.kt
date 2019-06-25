package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

data class MultiDependency(
    override val raw: RawDependency,
    override val targets: Set<TargetWithPostfix<*>>
) : PackageDependency, UnTypedDependency


operator fun RawDependency.invoke(target: TargetWithPostfix<*>): MultiDependency = MultiDependency(this, setOf(target))
operator fun RawDependency.invoke(targets: Set<TargetWithPostfix<*>>): MultiDependency = MultiDependency(this, targets)
operator fun RawDependency.invoke(vararg targets: TargetWithPostfix<*>): MultiDependency = MultiDependency(this, targets.toSet())
