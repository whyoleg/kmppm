package dev.whyoleg.kamp.base

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

typealias TargetProvider = KotlinMultiplatformExtension.(Target) -> KotlinTarget

sealed class Target(open val name: String, val configure: TargetProvider) {
    companion object {
        val common = CommonTarget()
        val jvm = JvmTarget()
        val android = AndroidTarget()
        val jvmBased = jvm + android

        val js = JsTarget()
        val linuxX64 = LinuxX64Target()
    }
}

data class CommonTarget(override val name: String = "common") : Target(name, { metadata() })

sealed class CommonBasedTarget(name: String, configure: TargetProvider) : Target(name, configure)

sealed class JvmBasedTarget(name: String, configure: TargetProvider) : CommonBasedTarget(name, configure)
data class JvmTarget(override val name: String = "jvm") : JvmBasedTarget(name, { jvm(it.name) })
data class AndroidTarget(override val name: String = "android") : JvmBasedTarget(name, { android(it.name) })

data class JsTarget(override val name: String = "js") : CommonBasedTarget(name, { js(it.name) })

data class LinuxX64Target(override val name: String = "linuxX64") : CommonBasedTarget(name, { linuxX64(it.name) })

operator fun <R : Target, T1 : R, T2 : R> T1.plus(other: T2): Set<R> = setOf(this, other)