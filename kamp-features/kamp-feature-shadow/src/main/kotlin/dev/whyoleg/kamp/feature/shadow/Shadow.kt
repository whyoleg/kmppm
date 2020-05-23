package dev.whyoleg.kamp.feature.shadow

import dev.whyoleg.kamp.dependency.*

object Shadow {
    const val defaultVersion = "5.2.0"
    const val plugin = "com.github.johnrengelman.shadow"
    fun dependency(version: String): KampDependency = dependency("com.github.jengelman.gradle.plugins", "shadow", version, KampPlatform.jvm)
}
