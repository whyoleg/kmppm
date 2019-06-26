package dev.whyoleg.kamp.packager

import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

interface Packager {
    val plugins: Set<Plugin>
    fun Project.configure()
}