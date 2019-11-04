package dev.whyoleg.kamp.dependency.classifier

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

interface GroupClassifier : Classifier {
    val group: String
}


fun GroupClassifier.raw(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider? = null
): RawDependency = RawDependency(group, name, version, provider)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    target: TargetWithPostfix<T>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, version, provider)(target)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    targets: Set<TargetWithPostfix<T>>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, version, provider)(targets)

fun <T : PlatformTarget> GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    vararg targets: TargetWithPostfix<T>,
    provider: DependencyProvider? = null
): TargetDependency<T> = raw(name, version, provider)(*targets)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    target: TargetWithPostfix<*>,
    provider: DependencyProvider? = null
): MultiDependency = raw(name, version, provider)(target)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    targets: Set<TargetWithPostfix<*>>,
    provider: DependencyProvider? = null
): MultiDependency = raw(name, version, provider)(targets)

fun GroupClassifier.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    vararg targets: TargetWithPostfix<*>,
    provider: DependencyProvider? = null
): MultiDependency = raw(name, version, provider)(*targets)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider? = null
): MultiDependency where C : GroupClassifier, C : MultiTargetClassifier = raw(name, version, provider)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider? = null
): TargetDependency<T> where C : GroupClassifier, C : TargetClassifier<T> = raw(name, version, provider)(targets)
