package dev.whyoleg.kamp.base

import dev.whyoleg.kamp.base.dependency.*
import dev.whyoleg.kamp.base.target.*

open class Group(val name: String) : MainTargets {
    override fun toString(): String = "Group(name='$name')"
}

fun Group.dependency(name: String, version: String? = null): RawLibraryDependency =
    RawLibraryDependency(this, name, version)

fun Group.version(version: String? = null): LibraryVersionDependency = LibraryVersionDependency(this, version)