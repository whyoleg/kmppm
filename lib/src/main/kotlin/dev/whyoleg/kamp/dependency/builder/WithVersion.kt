package dev.whyoleg.kamp.dependency.builder

import dev.whyoleg.kamp.dependency.*

interface WithVersion {
    val version: String
    val versionProviders: Set<RepositoryProvider>

    companion object {
        internal operator fun invoke(version: String, versionProvider: Array<out RepositoryProvider>): WithVersion = object : WithVersion {
            override val version: String get() = version
            override val versionProviders: Set<RepositoryProvider> get() = versionProvider.toSet()
        }
    }
}

fun Group.version(version: String, vararg versionProvider: RepositoryProvider): GroupWithVersion =
    object : GroupWithVersion, Group by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithPlatforms.version(version: String, vararg versionProvider: RepositoryProvider): GroupWithVersionPlatforms =
    object : GroupWithVersionPlatforms, GroupWithPlatforms by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithArtifact.version(version: String, vararg versionProvider: RepositoryProvider): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithArtifact by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithPlatformsArtifact.version(version: String, vararg versionProvider: RepositoryProvider): KampDependency =
    object : KampDependency, GroupWithPlatformsArtifact by this, WithVersion by WithVersion(version, versionProvider) {}
