package dev.whyoleg.kamp.platform

import org.jetbrains.kotlin.gradle.plugin.*

@Suppress("EnumEntryName")
enum class KampPlatform(val platformType: KotlinPlatformType) {
    common(KotlinPlatformType.common),
    jvm(KotlinPlatformType.jvm),
    android(KotlinPlatformType.androidJvm),
    js(KotlinPlatformType.js),
    native(KotlinPlatformType.native)
}

data class PlatformPostfix(val platform: KampPlatform, val postfix: String)
