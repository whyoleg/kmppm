package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

interface GroupVersionClassifier : GroupClassifier {
    val version: String
}

fun GroupVersionClassifier.raw(name: String): RawDependency = RawDependency(group, name, version)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, target: T): TargetDependency<T> = raw(name, version)(target)
fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, target: TargetWithPostfix<T>): TargetDependency<T> = raw(name, version)(target)

fun GroupVersionClassifier.dependency(name: String, vararg targets: Target): MultiDependency = raw(name, version)(targets.toSet())
fun GroupVersionClassifier.dependency(name: String, vararg targets: TargetWithPostfix<*>): MultiDependency = raw(name, version)(targets.toSet())
fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, vararg targets: T): TargetDependency<T> = raw(name, version).invoke<T>(targets.toSet())
fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, vararg targets: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, version)(targets.toSet())

@JvmName("dependency1")
fun GroupVersionClassifier.dependency(name: String, targets: Set<Target>): MultiDependency = raw(name, version)(targets)

fun GroupVersionClassifier.dependency(name: String, targets: Set<TargetWithPostfix<*>>): MultiDependency = raw(name, version)(targets)

@JvmName("dependency1")
fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, targets: Set<T>): TargetDependency<T> = raw(name, version).invoke<T>(targets)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, targets: Set<TargetWithPostfix<T>>): TargetDependency<T> = raw(name, version)(targets)

fun <C> C.dependency(name: String): MultiDependency where C : GroupVersionClassifier, C : MultiTargetClassifier = dependency(name, version, targets)
fun <C, T : PlatformTarget> C.dependency(name: String): TargetDependency<T> where C : GroupVersionClassifier, C : TargetClassifier<T> =
    dependency(name, version, targets)
