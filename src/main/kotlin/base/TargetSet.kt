@file:Suppress("FunctionName")

package dev.whyoleg.kamp.base

import kotlin.reflect.KClass

data class TargetSet<T : Target>(val name: String, val targetCls: KClass<out T>, val targets: Set<T>)

inline fun <reified T : Target> TargetSet(target: T): TargetSet<T> = TargetSet(target.name, T::class, setOf(target))

inline fun <reified T : Target> Set<T>.sourceSet(name: String): TargetSet<T> = TargetSet(name, T::class, this)
