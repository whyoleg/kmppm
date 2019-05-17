package dev.whyoleg.kmppm.base

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

typealias TargetProvider = KotlinMultiplatformExtension.(Target) -> KotlinTarget

sealed class Target(open val name: String, val configure: TargetProvider) {
    companion object {
        val Common = CommonTarget()
        val Jvm = JvmTarget()
        val Android = AndroidTarget()
        val Js = JsTarget()
        val LinuxX64 = LinuxX64Target()
    }
}

data class CommonTarget(override val name: String = "common") : Target(name, { metadata() })

sealed class JvmBased(name: String, configure: TargetProvider) : Target(name, configure)
data class JvmTarget(override val name: String = "jvm") : JvmBased(name, { jvm(it.name) })
data class AndroidTarget(override val name: String = "android") : JvmBased(name, { android(it.name) })

data class JsTarget(override val name: String = "js") : Target(name, { js(it.name) })

data class LinuxX64Target(override val name: String = "linuxX64") : Target(name, { linuxX64(it.name) })

operator fun <R : Target, T1 : R, T2 : R> T1.plus(other: T2): Set<R> = setOf(this, other)