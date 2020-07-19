package dev.whyoleg.kamp.feature.updates

import dev.whyoleg.kamp.dependency.*

object Updates {
    const val defaultVersion = "0.27.0"
    const val plugin = "com.github.ben-manes.versions"
    fun dependency(version: String): KampDependency =
        dependency("com.github.ben-manes", "gradle-versions-plugin", version, KampPlatform.jvm)
}
