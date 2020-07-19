package dev.whyoleg.kamp.dependency

import org.jetbrains.kotlin.gradle.plugin.*

@Suppress("EnumEntryName")
enum class KampPlatform(val platformType: KotlinPlatformType) {
    common(KotlinPlatformType.common),
    jvm(KotlinPlatformType.jvm),
    android(KotlinPlatformType.androidJvm),
    js(KotlinPlatformType.js),
    native(KotlinPlatformType.native);

    companion object {
        val oldStyle: List<PlatformPostfix>
            get() = listOf(
                common("common"),
                jvm(),
                android(),
                js("js"),
                native("native")
            )

        val newStyle: List<PlatformPostfix>
            get() = listOf(
                common(),
                jvm("jvm"),
                android("jvm"),
                js("js"),
                native("native")
            )

        private val map = KampPlatform.values().associateBy(KampPlatform::platformType)

        operator fun invoke(platformType: KotlinPlatformType): KampPlatform = map.getValue(platformType)
    }
}

data class PlatformPostfix(val platform: KampPlatform, val postfix: String)

operator fun KampPlatform.invoke(postfix: String = ""): PlatformPostfix = PlatformPostfix(this, postfix)
