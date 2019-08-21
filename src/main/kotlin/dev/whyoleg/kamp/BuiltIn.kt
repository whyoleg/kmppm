package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*

internal class BuiltIn(val versions: BuiltInVersions = BuiltInVersions()) {
    val dependencies = BuiltInDependencies(versions)
    val plugins = BuiltInPlugins(dependencies)

    internal companion object {
        val Default: BuiltIn = BuiltIn()
    }
}