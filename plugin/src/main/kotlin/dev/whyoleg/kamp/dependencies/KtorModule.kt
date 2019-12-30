package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*
import dev.whyoleg.kamp.version.*

inline class KtorVersion(val value: String) {
    companion object {
        val Stable = KtorVersion("1.2.6")
        val RC = KtorVersion("1.3.0-rc2")
    }
}

class KtorDependencies(version: KtorVersion) :
    GroupWithVersionPlatforms by group("io.ktor").version(version.value).platforms(jvm("jvm"), common()) {
    val client by lazy(::Client)

    inner class Client {
        val core = artifact("ktor-client-core")
        val websockets = artifact("ktor-client-websockets")
        val cio = artifact("ktor-client-cio").jvm
    }
}
