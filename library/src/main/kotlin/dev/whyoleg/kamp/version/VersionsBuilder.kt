package dev.whyoleg.kamp.version

class VersionsBuilder internal constructor() {
    internal val versionsMap: MutableMap<String, BuiltInVersions> = mutableMapOf()

    fun default(versions: BuiltInVersions) {
        versionsMap += BuiltInVersionsKind.Default.name.toLowerCase() to versions
    }

    infix fun String.use(versions: BuiltInVersions) {
        versionsMap += this.toLowerCase() to versions
    }

}

fun noVersionsRegistered(kind: String): Nothing = throw error("No versions registered for kind '${kind.toLowerCase()}'")
