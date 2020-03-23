package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependency : GroupWithVersionPlatforms, GroupWithVersionArtifact, GroupWithPlatformsArtifact

fun KampDependency.notation(platformType: KotlinPlatformType): String? =
    platforms.find { it.platform.platformType == platformType }?.let { (_, postfix) ->
        val p = if (postfix.isNotBlank()) "-$postfix" else ""
        "$group:$artifact$p:$version"
    }

val KampDependency.providers: Set<RepositoryProvider> get() = artifactProviders + versionProviders + groupProviders

fun dependency(group: String, artifact: String, version: String, platform: PlatformPostfix, vararg providers: RepositoryProvider): KampDependency =
    group(group).artifact(artifact).version(version, *providers).platforms(platform)

fun dependency(group: String, artifact: String, version: String, platforms: Iterable<PlatformPostfix>, vararg providers: RepositoryProvider): KampDependency =
    group(group).artifact(artifact).version(version, *providers).platforms(platforms)
