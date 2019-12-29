package dev.whyoleg.kamp.dependency

interface WithArtifact {
    val artifact: String
    val artifactProvider: RepositoryProvider?

    companion object {
        internal operator fun invoke(artifact: String, artifactProvider: RepositoryProvider? = null): WithArtifact = object : WithArtifact {
            override val artifact: String get() = artifact
            override val artifactProvider: RepositoryProvider? get() = artifactProvider
        }
    }
}

fun Group.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): GroupWithArtifact =
    object : GroupWithArtifact, Group by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithPlatforms.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): GroupWithPlatformsArtifact =
    object : GroupWithPlatformsArtifact, GroupWithPlatforms by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersion.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithVersion by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersionPlatforms.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): KampDependency =
    object : KampDependency, GroupWithVersionPlatforms by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}
