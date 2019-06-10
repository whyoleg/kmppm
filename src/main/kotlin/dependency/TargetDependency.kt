package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

data class TargetDependency<T : PlatformTarget>(
    override val raw: RawDependency,
    override val targets: Set<TargetWithPostfix<T>>
) : PackageDependency(raw, targets), TypedDependency<T>

operator fun <T : PlatformTarget> RawDependency.invoke(target: T): TargetDependency<T> = TargetDependency(this, setOf(target()))
operator fun <T : PlatformTarget> RawDependency.invoke(target: TargetWithPostfix<T>): TargetDependency<T> = TargetDependency(this, setOf(target))

operator fun <T : PlatformTarget> RawDependency.invoke(vararg targets: T): TargetDependency<T> = TargetDependency(this, targets.postfixed<T>())
operator fun <T : PlatformTarget> RawDependency.invoke(vararg targets: TargetWithPostfix<T>): TargetDependency<T> = TargetDependency(this, targets.toSet())

@JvmName("invoke1")
operator fun <T : PlatformTarget> RawDependency.invoke(targets: Set<T>): TargetDependency<T> = TargetDependency(this, targets.postfixed<T>())

operator fun <T : PlatformTarget> RawDependency.invoke(targets: Set<TargetWithPostfix<T>>): TargetDependency<T> = TargetDependency(this, targets)
