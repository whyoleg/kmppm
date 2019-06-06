package dev.whyoleg.kamp.base

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

typealias TargetProvider = KotlinMultiplatformExtension.(Target) -> KotlinTarget

sealed class Target(open val name: String, val configure: TargetProvider) {
    companion object {
        val common = MetaTarget()

        val jvm = JvmTarget()
        val jvm6 = Jvm6Target()

        val android = AndroidTarget()

        val jvmBased = jvm + jvm6

        val js = JsTarget()
        val linuxX64 = LinuxX64Target()
    }

    interface WithTargets {
        //fast accessors
        val common get() = Target.common
        val jvm get() = Target.jvm
        val jvm6 get() = Target.jvm6
        val android get() = Target.android
        val jvmBased get() = Target.jvmBased
        val js get() = Target.js
        val linuxX64 get() = Target.linuxX64
    }
}

data class MetaTarget(override val name: String = "common") : Target(name, { metadata() })

sealed class PlatformTarget(name: String, configure: TargetProvider) : Target(name, configure)

sealed class JvmBasedTarget(name: String, configure: TargetProvider) : PlatformTarget(name, configure)

data class JvmTarget(override val name: String = "jvm") : JvmBasedTarget(name, { jvm(it.name) })
data class Jvm6Target(override val name: String = "jvm6") : JvmBasedTarget(name, { jvm(it.name) })

data class AndroidTarget(override val name: String = "android") : JvmBasedTarget(name, { android(it.name) })

data class JsTarget(override val name: String = "js") : PlatformTarget(name, { js(it.name) })

data class LinuxX64Target(override val name: String = "linuxX64") : PlatformTarget(name, { linuxX64(it.name) })

operator fun <R : PlatformTarget, T1 : R, T2 : R> T1.plus(other: T2): Set<R> = setOf(this, other)