package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependency : GroupWithVersionPlatforms, GroupWithVersionArtifact, GroupWithPlatformsArtifact

fun KampDependency.notation(platformType: KotlinPlatformType): String? =
    platforms.find { it.platform.platformType == platformType }?.let { (_, postfix) ->
        val p = if (postfix.isNotBlank()) "-$postfix" else ""
        "$group:$artifact$p:$version"
    }

fun dependency(group: String, artifact: String, version: String, platforms: Iterable<PlatformPostfix>): KampDependency =
    group(group).artifact(artifact).version(version).platforms(platforms)

fun dependency(group: String, artifact: String, version: String, platform: PlatformPostfix): KampDependency =
    dependency(group, artifact, version, listOf(platform))

fun dependency(group: String, artifact: String, version: String, platform: KampPlatform): KampDependency =
    dependency(group, artifact, version, platform())

val KampDependency.d: Dependency
    get() = object : Dependency {
        override fun getGroup(): String? = this@d.group

        override fun getName(): String = this@d.artifact

        override fun getVersion(): String? = this@d.version

        override fun contentEquals(p0: Dependency): Boolean =
            this@d.group == p0.group && this@d.artifact == p0.name && this@d.version == p0.version

        override fun copy(): Dependency = this

        override fun because(reason: String?) {
        }

        override fun getReason(): String? = null
    }
