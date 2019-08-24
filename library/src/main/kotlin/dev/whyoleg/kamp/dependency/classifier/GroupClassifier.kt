package dev.whyoleg.kamp.dependency.classifier

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*

interface GroupClassifier : Classifier {
    val group: String
}


fun GroupClassifier.raw(name: String, version: (BuiltInVersions) -> String, provider: DependencyProvider): RawDependency =
    RawDependency(group, name, version, provider)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    target: TargetWithPostfix<T>
): TargetDependency<T> = raw(name, version, provider)(target)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    targets: Set<TargetWithPostfix<T>>
): TargetDependency<T> = raw(name, version, provider)(targets)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> = raw(name, version, provider)(*targets)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    target: TargetWithPostfix<*>
): MultiDependency =
    raw(name, version, provider)(target)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    targets: Set<TargetWithPostfix<*>>
): MultiDependency =
    raw(name, version, provider)(targets)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider,
    vararg targets: TargetWithPostfix<*>
): MultiDependency =
    raw(name, version, provider)(*targets)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider
): MultiDependency where C : GroupClassifier, C : MultiTargetClassifier =
    raw(name, version, provider)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider
): TargetDependency<T> where C : GroupClassifier, C : TargetClassifier<T> = raw(name, version, provider)(targets)
