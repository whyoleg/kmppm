package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*

open class Group(val name: String) : MainTargets {
    override fun toString(): String = "Group(name='$name')"
}

fun Group.dependency(name: String, version: String? = null): RawDependency =
    RawDependency(this, name, version)

fun Group.version(version: String? = null): LibraryVersionDependency = LibraryVersionDependency(this, version)