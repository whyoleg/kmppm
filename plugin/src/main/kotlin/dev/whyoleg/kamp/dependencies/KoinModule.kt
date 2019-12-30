package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.version.*
import dev.whyoleg.kamp.version.version

inline class KoinVersion(val value: String) {
    companion object {
        val Stable = KoinVersion("2.0.1")
        val Alpha = KoinVersion("2.1.0-alpha-8")
    }
}

class KoinDependencies(version: KoinVersion) :
    GroupWithVersionPlatforms by group("org.koin", "jcenter").version(version.value).jvm {
    val core = artifact("koin-core")
    val ext = artifact("koin-core-ext")
    val slf4j = artifact("koin-logger-slf4j")
}
