package dev.whyoleg.kamp.dependency.classifier

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

interface DependencyProviderClassifier : Classifier {
    val provider: DependencyProvider
}

fun <C> C.raw(
    name: String,
    version: (BuiltInVersions) -> String
): RawDependency where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version, provider)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    target: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version)(target)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    targets: Set<TargetWithPostfix<T>>
): TargetDependency<T> where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version)(*targets)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    target: TargetWithPostfix<*>
): MultiDependency where C : GroupClassifier, C : DependencyProviderClassifier =
    raw(name, version)(target)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    targets: Set<TargetWithPostfix<*>>
): MultiDependency where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version)(targets)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String,
    vararg targets: TargetWithPostfix<*>
): MultiDependency where C : GroupClassifier, C : DependencyProviderClassifier = raw(name, version)(*targets)

fun <C> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String
): MultiDependency where C : GroupClassifier, C : DependencyProviderClassifier, C : MultiTargetClassifier =
    raw(name, version)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    version: (BuiltInVersions) -> String
): TargetDependency<T> where C : GroupClassifier, C : DependencyProviderClassifier, C : TargetClassifier<T> = raw(name, version)(targets)


fun <C> C.raw(name: String): RawDependency where C : GroupVersionClassifier, C : DependencyProviderClassifier = raw(name, provider)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    target: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupVersionClassifier, C : DependencyProviderClassifier = raw(name)(target)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    targets: Set<TargetWithPostfix<T>>
): TargetDependency<T> where C : GroupVersionClassifier, C : DependencyProviderClassifier = raw(name)(targets)

fun <C, T : PlatformTarget> C.dependency(
    name: String,
    vararg targets: TargetWithPostfix<T>
): TargetDependency<T> where C : GroupVersionClassifier, C : DependencyProviderClassifier = raw(name)(*targets)

fun <C> C.dependency(name: String, target: TargetWithPostfix<*>): MultiDependency where C : GroupVersionClassifier, C : DependencyProviderClassifier =
    raw(name)(target)

fun <C> C.dependency(name: String, targets: Set<TargetWithPostfix<*>>): MultiDependency where C : GroupVersionClassifier, C : DependencyProviderClassifier =
    raw(name)(targets)

fun <C> C.dependency(name: String, vararg targets: TargetWithPostfix<*>): MultiDependency where C : GroupVersionClassifier, C : DependencyProviderClassifier =
    raw(name)(*targets)

fun <C> C.dependency(name: String): MultiDependency where C : GroupVersionClassifier, C : DependencyProviderClassifier, C : MultiTargetClassifier =
    raw(name)(targets)

fun <C, T : PlatformTarget> C.dependency(name: String): TargetDependency<T> where C : GroupVersionClassifier, C : DependencyProviderClassifier, C : TargetClassifier<T> =
    raw(name)(targets)
