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

fun GroupWithTargets.version(version: String, versionProvider: RepositoryProvider? = null): GroupWithVersionTargets =
    object : GroupWithVersionTargets, GroupWithTargets by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithArtifact.version(version: String, versionProvider: RepositoryProvider? = null): GroupWithVersionArtifact =
    object : GroupWithVersionArtifact, GroupWithArtifact by this, WithVersion by WithVersion(version, versionProvider) {}

fun GroupWithTargetsArtifact.version(version: String, versionProvider: RepositoryProvider? = null): Dependency =
    object : Dependency, GroupWithTargetsArtifact by this, WithVersion by WithVersion(version, versionProvider) {}
