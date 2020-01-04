package dev.whyoleg.kamp.dependency.builder

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.platform.*

interface WithPlatforms {
    val platforms: Iterable<PlatformPostfix>

    companion object {
        internal operator fun invoke(platforms: Iterable<PlatformPostfix>): WithPlatforms = object : WithPlatforms {
            override val platforms: Iterable<PlatformPostfix> get() = platforms
        }
    }
}

fun Group.platforms(platforms: Iterable<PlatformPostfix>): GroupWithPlatforms =
    object : GroupWithPlatforms, Group by this, WithPlatforms by WithPlatforms(platforms) {}

fun GroupWithVersion.platforms(platforms: Iterable<PlatformPostfix>): GroupWithVersionPlatforms =
    object : GroupWithVersionPlatforms, GroupWithVersion by this, WithPlatforms by WithPlatforms(platforms) {}

fun GroupWithArtifact.platforms(platforms: Iterable<PlatformPostfix>): GroupWithPlatformsArtifact =
    object : GroupWithPlatformsArtifact, GroupWithArtifact by this, WithPlatforms by WithPlatforms(platforms) {}

fun GroupWithVersionArtifact.platforms(platforms: Iterable<PlatformPostfix>): KampDependency =
    object : KampDependency, GroupWithVersionArtifact by this, WithPlatforms by WithPlatforms(platforms) {}


fun Group.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithPlatforms =
    platforms(platforms.toList() + platform)

fun GroupWithVersion.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithVersionPlatforms =
    platforms(platforms.toList() + platform)

fun GroupWithArtifact.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithPlatformsArtifact =
    platforms(platforms.toList() + platform)

fun GroupWithVersionArtifact.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): KampDependency =
    platforms(platforms.toList() + platform)

