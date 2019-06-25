package dev.whyoleg.kamp.packaging

import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

interface Packager {
    val plugins: Set<Plugin>
    fun Project.configure()
}