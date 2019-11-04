package dev.whyoleg.kamp.dependency.classifier

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

interface GroupVersionClassifier : GroupClassifier {
    val version: (BuiltInVersions) -> String
}

fun GroupVersionClassifier.raw(
    name: String,
    provider: DependencyProvider? = null
): RawDependency = raw(name, version, provider)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(
    name: String,
    target: TargetWithPostfix<T>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, provider)(target)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(
    name: String,
    targets: Set<TargetWithPostfix<T>>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, provider)(targets)

fun <T : PlatformTarget> GroupVersionClassifier.dependency(
    name: String,
    vararg targets: TargetWithPostfix<T>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, provider)(*targets)

fun GroupVersionClassifier.dependency(
    name: String,
    target: TargetWithPostfix<*>,
    provider: DependencyProvider? = null
): MultiDependency = raw(name, provider)(target)

fun GroupVersionClassifier.dependency(
    name: String,
    targets: Set<TargetWithPostfix<*>>,
    provider: DependencyProvider? = null
): MultiDependency = raw(name, provider)(targets)

fun GroupVersionClassifier.dependency(
    name: String,
    provider: DependencyProvider,
    vararg targets: TargetWithPostfix<*>
): MultiDependency = raw(name, provider)(*targets)


fun <C> C.dependency(
    name: String,
    provider: DependencyProvider? = null
): MultiDependency where C : GroupVersionClassifier, C : MultiTargetClassifier = raw(name, provider)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    provider: DependencyProvider? = null
): TargetDependency<T> where C : GroupVersionClassifier, C : TargetClassifier<T> = raw(name, provider)(targets)
