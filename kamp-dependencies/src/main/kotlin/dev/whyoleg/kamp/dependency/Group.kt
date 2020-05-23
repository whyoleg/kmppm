package dev.whyoleg.kamp.dependency

interface Group {
    val group: String
}

fun group(group: String): Group = object : Group {
    override val group: String get() = group
}

interface GroupWithVersion : Group, WithVersion
interface GroupWithPlatforms : Group, WithPlatforms
interface GroupWithArtifact : Group, WithArtifact

interface GroupWithVersionPlatforms : GroupWithVersion, GroupWithPlatforms
interface GroupWithVersionArtifact : GroupWithVersion, GroupWithArtifact
interface GroupWithPlatformsArtifact : GroupWithPlatforms, GroupWithArtifact
