package dev.whyoleg.kamp

import org.gradle.api.*

data class ProjectConfiguration(val group: String, val artifact: String, val version: Project.() -> String)