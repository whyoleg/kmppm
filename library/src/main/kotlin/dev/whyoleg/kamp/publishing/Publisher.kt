package dev.whyoleg.kamp.publishing

import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

interface Publisher {
    val plugins: Set<Plugin>
    fun Project.configure()
}