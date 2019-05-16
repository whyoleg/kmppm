package dev.whyoleg.kmppm.base

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.konan.target.KonanTarget

data class Target(
    val name: String,
    val type: KotlinPlatformType,
    val sub: KonanTarget? = null,
    val configure: KotlinMultiplatformExtension.(Target) -> KotlinTarget
) {
    companion object {
        val META = Target("common", KotlinPlatformType.common) { metadata() }
        val JVM = Target("jvm", KotlinPlatformType.jvm) { jvm(it.name) }
        val JS = Target("js", KotlinPlatformType.js) { js(it.name) }
        val LINUX_X64 = Target("linuxX64", KotlinPlatformType.native, KonanTarget.LINUX_X64) { linuxX64(it.name) }

        val ALL = setOf(JVM, JS, LINUX_X64)
    }
}

operator fun Target.plus(other: Target): Set<Target> = setOf(this, other)