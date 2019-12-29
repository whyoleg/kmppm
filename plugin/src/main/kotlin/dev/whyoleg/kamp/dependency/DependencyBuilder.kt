package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.platform.*

abstract class DependencyBuilder {
    companion object : DependencyBuilder()

    operator fun KampPlatform.invoke(postfix: String = ""): PlatformPostfix = PlatformPostfix(this, postfix)

    protected val jvm: KampPlatform get() = KampPlatform.Jvm
    protected val android: KampPlatform get() = KampPlatform.Android
    protected val js: KampPlatform get() = KampPlatform.Js
    protected val native: KampPlatform get() = KampPlatform.Native
    protected val common: KampPlatform get() = KampPlatform.Common

    val Group.jvm get() = platforms(jvm())
    val GroupWithVersion.jvm get() = platforms(jvm())
    val GroupWithArtifact.jvm get() = platforms(jvm())
    val GroupWithVersionArtifact.jvm get() = platforms(jvm())

    val Group.android get() = platforms(android())
    val GroupWithVersion.android get() = platforms(android())
    val GroupWithArtifact.android get() = platforms(android())
    val GroupWithVersionArtifact.android get() = platforms(android())

    val Group.js get() = platforms(js())
    val GroupWithVersion.js get() = platforms(js())
    val GroupWithArtifact.js get() = platforms(js())
    val GroupWithVersionArtifact.js get() = platforms(js())

    val Group.native get() = platforms(native())
    val GroupWithVersion.native get() = platforms(native())
    val GroupWithArtifact.native get() = platforms(native())
    val GroupWithVersionArtifact.native get() = platforms(native())

    val Group.common get() = platforms(common())
    val GroupWithVersion.common get() = platforms(common())
    val GroupWithArtifact.common get() = platforms(common())
    val GroupWithVersionArtifact.common get() = platforms(common())
}

@Suppress("unused")
fun DependencyBuilder.group(group: String, groupProvider: RepositoryProvider? = null): Group = Group(group, groupProvider)
