package dev.whyoleg.kamp.configuration

import org.gradle.api.*

data class ProjectConfiguration(val group: String, val artifact: String, val version: Project.() -> String)

@Suppress("FunctionName")
fun ProjectConfiguration(group: String, artifact: String, version: String): ProjectConfiguration =
    ProjectConfiguration(group, artifact) { version }
