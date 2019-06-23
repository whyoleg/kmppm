package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface GroupVersionClassifier : GroupClassifier {
    val version: String
}

fun GroupVersionClassifier.raw(name: String, provider: DependencyProvider): RawDependency = raw(name, version, provider)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(name: String, provider: DependencyProvider, target: TargetWithPostfix<T>): TargetDependency<T> =
    raw(name, provider)(target)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(
    name: String,
    provider: DependencyProvider,
    targets: Set<TargetWithPostfix<T>>
): TargetDependency<T> = raw(name, provider)(targets)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(
    name: String,
    provider: DependencyProvider,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> = raw(name, provider)(*targets)

fun GroupVersionClassifier.dependency(name: String, provider: DependencyProvider, target: TargetWithPostfix<*>): MultiDependency = raw(name, provider)(target)
fun GroupVersionClassifier.dependency(name: String, provider: DependencyProvider, targets: Set<TargetWithPostfix<*>>): MultiDependency =
    raw(name, provider)(targets)

fun GroupVersionClassifier.dependency(name: String, provider: DependencyProvider, vararg targets: TargetWithPostfix<*>): MultiDependency =
    raw(name, provider)(*targets)


fun <C> C.dependency(name: String, provider: DependencyProvider): MultiDependency where C : GroupVersionClassifier, C : MultiTargetClassifier =
    raw(name, provider)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    provider: DependencyProvider
): TargetDependency<T> where C : GroupVersionClassifier, C : TargetClassifier<T> = raw(name, provider)(targets)
