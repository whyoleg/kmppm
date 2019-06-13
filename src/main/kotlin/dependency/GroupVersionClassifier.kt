package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface VersionClassifier {
    var version: String
}

interface GroupVersionClassifier : GroupClassifier, VersionClassifier


fun <C> C.raw(name: String): RawDependency where C : GroupClassifier, C : VersionClassifier = RawDependency(group, name, version)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    target: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupClassifier, C : VersionClassifier = raw(name)(target)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    targets: Set<TargetWithPostfix<T>>
): TargetDependency<T> where C : GroupClassifier, C : VersionClassifier = raw(name)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupClassifier, C : VersionClassifier = raw(name)(*targets)

fun <C> C.dependency(name: String, target: TargetWithPostfix<*>): MultiDependency where C : GroupClassifier, C : VersionClassifier = raw(name)(target)
fun <C> C.dependency(name: String, targets: Set<TargetWithPostfix<*>>): MultiDependency where C : GroupClassifier, C : VersionClassifier = raw(name)(targets)
fun <C> C.dependency(name: String, vararg targets: TargetWithPostfix<*>): MultiDependency where C : GroupClassifier, C : VersionClassifier = raw(name)(*targets)

fun <C> C.dependency(name: String): MultiDependency where C : GroupClassifier, C : VersionClassifier, C : MultiTargetClassifier =
    raw(name)(targets)

fun <C, T : PlatformTarget> C.dependency(name: String): TargetDependency<T> where C : GroupClassifier, C : VersionClassifier, C : TargetClassifier<T> =
    raw(name)(targets)
