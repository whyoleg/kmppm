package dev.whyoleg.kamp

import net.nemerosa.versioning.*
import org.gradle.api.*

data class ProjectConfiguration(
    val group: String,
    val artifact: String,
    val version: VersioningExtension.() -> String
)

val Project.versioning get() = extensions.getByName("versioning") as VersioningExtension