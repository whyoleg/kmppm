package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

interface Classifier : MainTargets

interface MultiTargetClassifier : Classifier {
    val targets: Set<TargetWithPostfix<*>>
}

interface TargetClassifier<T : PlatformTarget> : Classifier {
    val targets: Set<TargetWithPostfix<T>>
}

fun MultiTargetClassifier.dependency(
    group: String,
    name: String,
    version: String
): MultiDependency = RawDependency(group, name, version)(targets)

fun <T : PlatformTarget> TargetClassifier<T>.dependency(
    group: String,
    name: String,
    version: String
): TargetDependency<T> = RawDependency(group, name, version)(targets)

inline operator fun <C : Classifier> C.invoke(block: C.() -> Unit): Unit = block()