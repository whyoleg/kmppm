package dev.whyoleg.kamp.dependency

interface Dependency : GroupWithVersionTargets, GroupWithVersionArtifact, GroupWithTargetsArtifact

interface GroupWithVersionTargets : GroupWithVersion, GroupWithTargets
interface GroupWithVersionArtifact : GroupWithVersion, GroupWithArtifact
interface GroupWithTargetsArtifact : GroupWithTargets, GroupWithArtifact

interface GroupWithVersion : Group, WithVersion
interface GroupWithTargets : Group, WithTargets
interface GroupWithArtifact : Group, WithArtifact

val Dependency.notation: String get() = "$group:$artifact:$version"
val Dependency.provider: RepositoryProvider? get() = artifactProvider ?: versionProvider ?: groupProvider
