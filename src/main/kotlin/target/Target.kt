package dev.whyoleg.kamp.target

import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

typealias TargetProvider = KotlinMultiplatformExtension.(Target) -> KotlinTarget

abstract class Target(open val name: String, val configure: TargetProvider) {
    companion object : MainTargets
}

data class MetaTarget(override val name: String = "common") : Target(name, { metadata() })

abstract class PlatformTarget(name: String, configure: TargetProvider) : Target(name, configure)

operator fun <R : PlatformTarget, T1 : R, T2 : R> T1.plus(other: T2): Set<R> = setOf(this, other)