package dev.whyoleg.kamp.feature.jib

import dev.whyoleg.kamp.dependency.*

object Jib {
    const val defaultVersion = "1.6.1"
    const val plugin = "com.google.cloud.tools.jib"
    fun dependency(version: String): KampDependency =
        dependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", version, KampPlatform.jvm)
}
