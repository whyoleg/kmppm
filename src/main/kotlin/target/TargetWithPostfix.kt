package dev.whyoleg.kamp.target

data class TargetWithPostfix<T : Target>(val target: T, val postfix: String?)

operator fun <T : Target> T.invoke(postfix: String? = null): TargetWithPostfix<T> = TargetWithPostfix(this, postfix)

fun <T : PlatformTarget> Iterable<T>.postfixed(): Set<TargetWithPostfix<T>> = map { it.invoke() }.toSet()
fun <T : PlatformTarget> Array<out T>.postfixed(): Set<TargetWithPostfix<T>> = map { it.invoke() }.toSet()

@JvmName("postfixed1")
fun Iterable<Target>.postfixed(): Set<TargetWithPostfix<*>> = map { it.invoke() }.toSet()

@JvmName("postfixed1")
fun Array<out Target>.postfixed(): Set<TargetWithPostfix<*>> = map { it.invoke() }.toSet()
