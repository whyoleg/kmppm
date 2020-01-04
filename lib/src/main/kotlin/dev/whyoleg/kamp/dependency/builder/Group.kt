package dev.whyoleg.kamp.dependency.builder

import dev.whyoleg.kamp.dependency.*

interface Group {
    val group: String
    val groupProvider: RepositoryProvider?

    companion object {
        internal operator fun invoke(group: String, groupProvider: RepositoryProvider? = null): Group = object : Group {
            override val group: String get() = group
            override val groupProvider: RepositoryProvider? get() = groupProvider
        }
    }
}

fun group(group: String, groupProvider: RepositoryProvider? = null): Group = Group(group, groupProvider)

interface GroupWithVersion : Group, WithVersion
interface GroupWithPlatforms : Group, WithPlatforms
interface GroupWithArtifact : Group, WithArtifact

interface GroupWithVersionPlatforms : GroupWithVersion, GroupWithPlatforms
interface GroupWithVersionArtifact : GroupWithVersion, GroupWithArtifact
interface GroupWithPlatformsArtifact : GroupWithPlatforms, GroupWithArtifact
