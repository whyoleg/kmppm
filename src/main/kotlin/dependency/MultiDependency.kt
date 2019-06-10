package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

data class MultiDependency(
    override val raw: RawDependency,
    override val targets: Set<TargetWithPostfix<*>>
) : PackageDependency(raw, targets), UnTypedDependency


operator fun RawDependency.invoke(target: Target): MultiDependency = MultiDependency(this, setOf(target()))
operator fun RawDependency.invoke(target: TargetWithPostfix<*>): MultiDependency = MultiDependency(this, setOf(target))

operator fun RawDependency.invoke(vararg targets: Target): MultiDependency = invoke(targets.postfixed())
operator fun RawDependency.invoke(vararg targets: TargetWithPostfix<*>): MultiDependency = invoke(targets.toSet())

@JvmName("invoke1")
operator fun RawDependency.invoke(targets: Set<Target>): MultiDependency = MultiDependency(this, targets.postfixed())

operator fun RawDependency.invoke(targets: Set<TargetWithPostfix<*>>): MultiDependency = MultiDependency(this, targets)
