@file:Suppress("FunctionName")

package dev.whyoleg.kamp.target

import kotlin.reflect.*

data class MultiTarget<T : Target>(val name: String, val targetCls: KClass<out T>, val targets: Set<T>)

inline fun <reified T : Target> MultiTarget(target: T): MultiTarget<T> =
    MultiTarget(target.name, T::class, setOf(target)) //TODO check if reified can be removed

inline fun <reified T : Target> Set<T>.named(name: String): MultiTarget<T> =
    MultiTarget(name, T::class, this)
