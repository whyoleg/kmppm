package dev.whyoleg.kamp.dependency.builder

import dev.whyoleg.kamp.dependency.*

interface Group {
    val group: String
    val groupProviders: Set<RepositoryProvider>

    companion object {
        internal operator fun invoke(group: String, groupProvider: Array<out RepositoryProvider>): Group = object : Group {
            override val group: String get() = group
            override val groupProviders: Set<RepositoryProvider> get() = groupProvider.toSet()
        }
    }
}

fun group(group: String, vararg groupProvider: RepositoryProvider): Group = Group(group, groupProvider)

interface GroupWithVersion : Group, WithVersion
interface GroupWithPlatforms : Group, WithPlatforms
interface GroupWithArtifact : Group, WithArtifact

interface GroupWithVersionPlatforms : GroupWithVersion, GroupWithPlatforms
interface GroupWithVersionArtifact : GroupWithVersion, GroupWithArtifact
interface GroupWithPlatformsArtifact : GroupWithPlatforms, GroupWithArtifact
