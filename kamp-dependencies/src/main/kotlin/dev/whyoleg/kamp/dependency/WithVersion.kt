package dev.whyoleg.kamp.dependency

interface WithVersion {
    val version: String
}

private fun withVersion(version: String): WithVersion = object : WithVersion {
    override val version: String get() = version
}

fun Group.version(version: String): GroupWithVersion =
    object : GroupWithVersion, Group by this, WithVersion by withVersion(version) {}

fun GroupWithPlatforms.version(version: String): GroupWithVersionPlatforms =
    object : GroupWithVersionPlatforms, GroupWithPlatforms by this, WithVersion by withVersion(version) {}

fun GroupWithArtifact.version(version: String): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithArtifact by this, WithVersion by withVersion(version) {}

fun GroupWithPlatformsArtifact.version(version: String): KampDependency =
    object : KampDependency, GroupWithPlatformsArtifact by this, WithVersion by withVersion(version) {}
