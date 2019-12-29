package dev.whyoleg.kamp.dependency

import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependency : GroupWithVersionPlatforms, GroupWithVersionArtifact, GroupWithPlatformsArtifact

interface GroupWithVersionPlatforms : GroupWithVersion, GroupWithPlatforms
interface GroupWithVersionArtifact : GroupWithVersion, GroupWithArtifact
interface GroupWithPlatformsArtifact : GroupWithPlatforms, GroupWithArtifact

interface GroupWithVersion : Group, WithVersion
interface GroupWithPlatforms : Group, WithPlatforms
interface GroupWithArtifact : Group, WithArtifact

fun KampDependency.notation(platformType: KotlinPlatformType): String? =
    platforms.find { it.platform.platformType == platformType }?.let { (_, postfix) ->
        val p = if (postfix.isNotBlank()) "-$postfix" else ""
        "$group:$artifact$p:$version"
    }

val KampDependency.provider: RepositoryProvider? get() = artifactProvider ?: versionProvider ?: groupProvider
