package dev.whyoleg.kamp.dependency

interface WithArtifact {
    val artifact: String
}

private fun withArtifact(artifact: String): WithArtifact = object : WithArtifact {
    override val artifact: String get() = artifact
}

fun Group.artifact(artifact: String): GroupWithArtifact =
    object : GroupWithArtifact, Group by this, WithArtifact by withArtifact(artifact) {}

fun GroupWithPlatforms.artifact(artifact: String): GroupWithPlatformsArtifact =
    object : GroupWithPlatformsArtifact, GroupWithPlatforms by this, WithArtifact by withArtifact(artifact) {}

fun GroupWithVersion.artifact(artifact: String): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithVersion by this, WithArtifact by withArtifact(artifact) {}

fun GroupWithVersionPlatforms.artifact(artifact: String): KampDependency =
    object : KampDependency, GroupWithVersionPlatforms by this, WithArtifact by withArtifact(artifact) {}
