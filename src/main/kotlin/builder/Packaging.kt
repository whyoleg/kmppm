package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

interface Packaging {
    val plugins: Set<Plugin>
    fun Project.configure()
}