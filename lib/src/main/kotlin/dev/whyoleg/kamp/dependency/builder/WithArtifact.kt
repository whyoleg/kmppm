package dev.whyoleg.kamp.dependency.builder

import dev.whyoleg.kamp.dependency.*

interface WithArtifact {
    val artifact: String
    val artifactProviders: Set<RepositoryProvider>

    companion object {
        internal operator fun invoke(artifact: String, artifactProvider: Array<out RepositoryProvider>): WithArtifact =
            object : WithArtifact {
                override val artifact: String get() = artifact
                override val artifactProviders: Set<RepositoryProvider> get() = artifactProvider.toSet()
            }
    }
}

fun Group.artifact(artifact: String, vararg artifactProvider: RepositoryProvider): GroupWithArtifact =
    object : GroupWithArtifact, Group by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithPlatforms.artifact(artifact: String, vararg artifactProvider: RepositoryProvider): GroupWithPlatformsArtifact =
    object : GroupWithPlatformsArtifact, GroupWithPlatforms by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersion.artifact(artifact: String, vararg artifactProvider: RepositoryProvider): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithVersion by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}

fun GroupWithVersionPlatforms.artifact(artifact: String, vararg artifactProvider: RepositoryProvider): KampDependency =
    object : KampDependency, GroupWithVersionPlatforms by this, WithArtifact by WithArtifact(artifact, artifactProvider) {}
