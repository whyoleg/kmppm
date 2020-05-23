package dev.whyoleg.kamp.dependency

interface WithPlatforms {
    val platforms: Iterable<PlatformPostfix>
}

private fun withPlatforms(platforms: Iterable<PlatformPostfix>): WithPlatforms = object : WithPlatforms {
    override val platforms: Iterable<PlatformPostfix> get() = platforms
}

fun Group.platforms(platforms: Iterable<PlatformPostfix>): GroupWithPlatforms =
    object : GroupWithPlatforms, Group by this, WithPlatforms by withPlatforms(platforms) {}

fun GroupWithVersion.platforms(platforms: Iterable<PlatformPostfix>): GroupWithVersionPlatforms =
    object : GroupWithVersionPlatforms, GroupWithVersion by this, WithPlatforms by withPlatforms(platforms) {}

fun GroupWithArtifact.platforms(platforms: Iterable<PlatformPostfix>): GroupWithPlatformsArtifact =
    object : GroupWithPlatformsArtifact, GroupWithArtifact by this, WithPlatforms by withPlatforms(platforms) {}

fun GroupWithVersionArtifact.platforms(platforms: Iterable<PlatformPostfix>): KampDependency =
    object : KampDependency, GroupWithVersionArtifact by this, WithPlatforms by withPlatforms(platforms) {}


fun Group.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithPlatforms =
    platforms(platforms.toList() + platform)

fun GroupWithVersion.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithVersionPlatforms =
    platforms(platforms.toList() + platform)

fun GroupWithArtifact.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): GroupWithPlatformsArtifact =
    platforms(platforms.toList() + platform)

fun GroupWithVersionArtifact.platforms(platform: PlatformPostfix, vararg platforms: PlatformPostfix): KampDependency =
    platforms(platforms.toList() + platform)


fun Group.platforms(platform: KampPlatform): GroupWithPlatforms = platforms(platform())

fun GroupWithVersion.platforms(platform: KampPlatform): GroupWithVersionPlatforms = platforms(platform())

fun GroupWithArtifact.platforms(platform: KampPlatform): GroupWithPlatformsArtifact = platforms(platform())

fun GroupWithVersionArtifact.platforms(platform: KampPlatform): KampDependency = platforms(platform())
