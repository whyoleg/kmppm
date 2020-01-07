package dev.whyoleg.kamp.platform

import dev.whyoleg.kamp.dependency.builder.*
import org.jetbrains.kotlin.gradle.plugin.*

@Suppress("EnumEntryName")
enum class KampPlatform(val platformType: KotlinPlatformType) {
    common(KotlinPlatformType.common),
    jvm(KotlinPlatformType.jvm),
    android(KotlinPlatformType.androidJvm),
    js(KotlinPlatformType.js),
    native(KotlinPlatformType.native);

    companion object {
        fun oldStyle(): List<PlatformPostfix> = listOf(common("common"), jvm(), android(), js("js"), native("native"))
    }
}

data class PlatformPostfix(val platform: KampPlatform, val postfix: String)
