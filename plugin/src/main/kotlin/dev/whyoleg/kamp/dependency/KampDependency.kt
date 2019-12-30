package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.dependency.builder.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependency : GroupWithVersionPlatforms, GroupWithVersionArtifact, GroupWithPlatformsArtifact

fun KampDependency.notation(platformType: KotlinPlatformType): String? =
    platforms.find { it.platform.platformType == platformType }?.let { (_, postfix) ->
        val p = if (postfix.isNotBlank()) "-$postfix" else ""
        "$group:$artifact$p:$version"
    }

val KampDependency.provider: RepositoryProvider? get() = artifactProvider ?: versionProvider ?: groupProvider
