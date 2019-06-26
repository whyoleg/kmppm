package dev.whyoleg.kamp.target.configuration

import dev.whyoleg.kamp.target.*

internal data class TargetConfiguration(
    val target: PlatformTarget,
    val options: TargetOptions
)

class TargetConfigurationBuilder {

    private val configurations = mutableMapOf<PlatformTarget, TargetOptions>()

    operator fun JvmTarget.invoke(block: JvmTargetOptions.() -> Unit = {}) {
        (configurations.getOrPut(this) { JvmTargetOptions() } as JvmTargetOptions).apply(block)
    }

    operator fun JsTarget.invoke(block: JsTargetOptions.() -> Unit = {}) {
        (configurations.getOrPut(this) { JsTargetOptions() } as JsTargetOptions).apply(block)
    }

    internal fun data(): Set<TargetConfiguration> = configurations.map { (target, options) -> TargetConfiguration(target, options) }.toSet()
}