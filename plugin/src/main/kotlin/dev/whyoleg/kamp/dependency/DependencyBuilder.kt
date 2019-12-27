package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target

abstract class DependencyBuilder {
    companion object : DependencyBuilder()

    operator fun Target.invoke(postfix: String? = null): TargetWithPostfix = TargetWithPostfix(this, postfix)

    val jvm: Target get() = Target.Jvm
    val android: Target get() = Target.Android
    val js: Target get() = Target.Js
    val native: Target get() = Target.Native
    val common: Target get() = Target.Common

    val Group.jvm get() = targets(jvm())
    val GroupWithVersion.jvm get() = targets(jvm())
    val GroupWithArtifact.jvm get() = targets(jvm())
    val GroupWithVersionArtifact.jvm get() = targets(jvm())
}

@Suppress("unused")
fun DependencyBuilder.group(group: String, groupProvider: RepositoryProvider? = null): Group = Group(group, groupProvider)
