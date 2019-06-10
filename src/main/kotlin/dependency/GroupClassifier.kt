package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

interface GroupClassifier : Classifier {
    val group: String
}

fun GroupClassifier.raw(name: String, version: String): RawDependency = RawDependency(group, name, version)

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, target: T): TargetDependency<T> = raw(name, version)(target)
fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, target: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, version)(target)

fun GroupClassifier.dependency(name: String, version: String, vararg targets: Target): MultiDependency = raw(name, version)(targets.toSet())
fun GroupClassifier.dependency(name: String, version: String, vararg targets: TargetWithPostfix<*>): MultiDependency = raw(name, version)(targets.toSet())
fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, vararg targets: T): TargetDependency<T> =
    raw(name, version).invoke<T>(targets.toSet())

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, vararg targets: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, version)(targets.toSet())

@JvmName("dependency1")
fun GroupClassifier.dependency(name: String, version: String, targets: Set<Target>): MultiDependency = raw(name, version)(targets)

fun GroupClassifier.dependency(name: String, version: String, targets: Set<TargetWithPostfix<*>>): MultiDependency = raw(name, version)(targets)

@JvmName("dependency1")
fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, targets: Set<T>): TargetDependency<T> = raw(name, version).invoke<T>(targets)

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, targets: Set<TargetWithPostfix<T>>): TargetDependency<T> =
    raw(name, version)(targets)

fun <C> C.dependency(name: String, version: String): MultiDependency where C : GroupClassifier, C : MultiTargetClassifier = dependency(name, version, targets)
fun <C, T : PlatformTarget> C.dependency(name: String, version: String): TargetDependency<T> where C : GroupClassifier, C : TargetClassifier<T> =
    dependency(name, version, targets)
