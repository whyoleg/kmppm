package dev.whyoleg.kmppm

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.konan.target.KonanTarget

interface NamedTarget {
    val name: String

    fun targets(): Set<NamedTarget> = when {
        this is TargetSet -> targets.flatMap(NamedTarget::targets).toSet()
        this is Target -> setOf(this)
        else -> emptySet()
    }
}

data class Target(
    override val name: String,
    val type: KotlinPlatformType,
    val sub: KonanTarget? = null,
    val configure: KotlinMultiplatformExtension.(Target) -> KotlinTarget
) : NamedTarget {

    fun config(kotlin: KotlinMultiplatformExtension) {
        configure(kotlin, this)
    }

    companion object {
        val META = Target("common", KotlinPlatformType.common) { metadata() }
        val JVM = Target("jvm", KotlinPlatformType.jvm) { jvm(it.name) }
        val JS = Target("js", KotlinPlatformType.js) { js(it.name) }
        val LINUX_X64 = Target("linuxX64", KotlinPlatformType.native, KonanTarget.LINUX_X64) { linuxX64(it.name) }

        val ALL: TargetSet = setOf(JVM, JS, LINUX_X64).named("all")
    }
}

operator fun Target.plus(other: Target): Set<Target> = setOf(this, other)

fun Set<Target>.named(name: String) = TargetSet(name, this)

data class TargetSet(override val name: String, val targets: Set<NamedTarget>) : NamedTarget
