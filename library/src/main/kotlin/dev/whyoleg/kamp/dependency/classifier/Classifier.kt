package dev.whyoleg.kamp.dependency.classifier

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

interface Classifier : MainTargets, DependencyProviders

inline operator fun <C : Classifier> C.invoke(block: C.() -> Unit): Unit = block()

interface MultiTargetClassifier : Classifier {
    val targets: Set<TargetWithPostfix<*>>
}

interface TargetClassifier<T : PlatformTarget> : Classifier {
    val targets: Set<TargetWithPostfix<T>>
}

fun MultiTargetClassifier.dependency(
    group: String,
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider? = null
): MultiDependency = RawDependency(group, name, version, provider)(targets)

fun <T : PlatformTarget> TargetClassifier<T>.dependency(
    group: String,
    name: String,
    version: (BuiltInVersions) -> String,
    provider: DependencyProvider? = null
): TargetDependency<T> = RawDependency(group, name, version, provider)(targets)
