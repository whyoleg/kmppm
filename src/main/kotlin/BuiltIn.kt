package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*

class BuiltIn(val versions: BuiltInVersions = BuiltInVersions()) {
    val dependencies = BuiltInDependencies(versions)
    val plugins = BuiltInPlugins(dependencies)

    companion object {
        val Default: BuiltIn = BuiltIn()
    }
}