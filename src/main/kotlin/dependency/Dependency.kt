package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

interface Dependency
interface TypedDependency<T : Target> : Dependency
interface UnTypedDependency : Dependency

fun Dependency(group: String, name: String, version: String, target: TargetWithPostfix<*>): MultiDependency =
    RawDependency(group, name, version)(target)

fun Dependency(group: String, name: String, version: String, targets: Set<TargetWithPostfix<*>>): MultiDependency =
    RawDependency(group, name, version)(targets)

fun Dependency(group: String, name: String, version: String, target: TargetWithPostfix<*>, vararg targets: TargetWithPostfix<*>): MultiDependency =
    RawDependency(group, name, version)(targets.toSet() + target)

fun <T : PlatformTarget> Dependency(group: String, name: String, version: String, target: TargetWithPostfix<T>): TargetDependency<T> =
    RawDependency(group, name, version)(target)

fun <T : PlatformTarget> Dependency(group: String, name: String, version: String, targets: Set<TargetWithPostfix<T>>): TargetDependency<T> =
    RawDependency(group, name, version)(targets)

fun <T : PlatformTarget> Dependency(
    group: String,
    name: String,
    version: String,
    target: TargetWithPostfix<T>,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> =
    RawDependency(group, name, version)(targets.toSet() + target)
