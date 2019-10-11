package dev.whyoleg.kamp.version

import dev.whyoleg.kamp.*

class VersionsBuilder internal constructor() {
    internal val versionsMap: MutableMap<String, BuiltInVersions> = mutableMapOf()

    fun default(versions: BuiltInVersions) {
        versionsMap += defaultVersionsKind to versions
    }

    infix fun String.use(versions: BuiltInVersions) {
        versionsMap += this.toLowerCase() to versions
    }

}

internal fun noVersionsRegistered(kind: String): Nothing = throw error("No versions registered for kind '${kind.toLowerCase()}'")
