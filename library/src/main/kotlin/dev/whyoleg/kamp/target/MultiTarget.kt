@file:Suppress("FunctionName")

package dev.whyoleg.kamp.target

import kotlin.reflect.*

data class MultiTarget<T : Target>(val name: String, val targetCls: KClass<out T>, val targets: Set<T>)

fun <T : Target> MultiTarget(target: T): MultiTarget<T> = MultiTarget(target.name, target::class, setOf(target))

inline fun <reified T : Target> Set<T>.named(name: String): MultiTarget<T> = MultiTarget(name, T::class, this)
