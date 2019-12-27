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

fun GroupWithTargets.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): GroupWithTargetsArtifact =
    object : GroupWithTargetsArtifact, GroupWithTargets by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersion.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithVersion by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersionTargets.artifact(artifact: String, artifactProvider: RepositoryProvider? = null): Dependency =
    object : Dependency, GroupWithVersionTargets by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}
