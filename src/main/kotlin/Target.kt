package dev.whyoleg.kmppm

import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.konan.target.*

interface NamedTarget {
    val name: String

    fun targets(): Set<NamedTarget> = when {
        this is TargetSet -> targets.flatMap(NamedTarget::targets).toSet()
        this is Target    -> setOf(this)
        else              -> emptySet()
    }
}

data class Target(
    override val name: String,
    val type: KotlinPlatformType,
    val sub: KonanTarget? = null,
    val configure: KotlinMultiplatformExtension.(Target) -> KotlinTarget
) : NamedTarget {
    companion object {
        val META = Target("common", KotlinPlatformType.common) { metadata() }
        val JVM = Target("JVM", KotlinPlatformType.jvm) { jvm(it.name) }
    }
}

operator fun Target.plus(other: Target): Set<Target> = setOf(this, other)

fun Set<Target>.named(name: String) = TargetSet(name, this)

data class TargetSet(override val name: String, val targets: Set<NamedTarget>) : NamedTarget
