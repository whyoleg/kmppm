package dev.whyoleg.kamp.target

import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

typealias TargetProvider = KotlinMultiplatformExtension.() -> KotlinTarget

abstract class Target(val provider: TargetProvider) {
    abstract val name: String

    companion object : MainTargets
}

data class MetaTarget(override val name: String = "common") : Target({ metadata() })

abstract class PlatformTarget(configure: TargetProvider) : Target(configure)
