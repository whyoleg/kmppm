package dev.whyoleg.kamp.platform

import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.*

enum class KampPlatform(val platformType: KotlinPlatformType) {
    Common(common),
    Jvm(jvm),
    Android(androidJvm),
    Js(js),
    Native(native)
}

data class PlatformPostfix(val platform: KampPlatform, val postfix: String)
