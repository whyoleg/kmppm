package dev.whyoleg.kamp.dependency

interface WithVersion {
    val version: String
    val versionProvider: RepositoryProvider?

    companion object {
        internal operator fun invoke(version: String, versionProvider: RepositoryProvider? = null): WithVersion = object : WithVersion {
            override val version: String get() = version
            override val versionProvider: RepositoryProvider? get() = versionProvider
        }
    }
}

fun Group.version(version: String, versionProvider: RepositoryProvider? = null): GroupWithVersion =
    object : GroupWithVersion, Group by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithPlatforms.version(version: String, versionProvider: RepositoryProvider? = null): GroupWithVersionPlatforms =
    object : GroupWithVersionPlatforms, GroupWithPlatforms by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithArtifact.version(version: String, versionProvider: RepositoryProvider? = null): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithArtifact by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithPlatformsArtifact.version(version: String, versionProvider: RepositoryProvider? = null): KampDependency =
    object : KampDependency, GroupWithPlatformsArtifact by this, WithVersion by WithVersion(version, versionProvider) {}
