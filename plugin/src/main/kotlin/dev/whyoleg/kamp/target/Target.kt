package dev.whyoleg.kamp.target

import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.*

enum class Target(val platformType: KotlinPlatformType) {
    Common(common),
    Jvm(jvm),
    Android(androidJvm),
    Js(js),
    Native(native)
}
