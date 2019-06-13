package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface GroupClassifier : Classifier {
    val group: String
}


fun GroupClassifier.raw(name: String, version: String): RawDependency = RawDependency(group, name, version)

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, target: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, version)(target)

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, targets: Set<TargetWithPostfix<T>>): TargetDependency<T> =
    raw(name, version)(targets)

fun <T : PlatformTarget> GroupClassifier.dependency(name: String, version: String, vararg targets: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, version)(*targets)

fun GroupClassifier.dependency(name: String, version: String, target: TargetWithPostfix<*>): MultiDependency = raw(name, version)(target)
fun GroupClassifier.dependency(name: String, version: String, targets: Set<TargetWithPostfix<*>>): MultiDependency = raw(name, version)(targets)
fun GroupClassifier.dependency(name: String, version: String, vararg targets: TargetWithPostfix<*>): MultiDependency = raw(name, version)(*targets)

fun <C> C.dependency(name: String, version: String): MultiDependency where C : GroupClassifier, C : MultiTargetClassifier =
    raw(name, version)(targets)

fun <C, T : PlatformTarget> C.dependency(name: String, version: String): TargetDependency<T> where C : GroupClassifier, C : TargetClassifier<T> =
    raw(name, version)(targets)
