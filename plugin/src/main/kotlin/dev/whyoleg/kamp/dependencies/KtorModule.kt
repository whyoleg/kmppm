package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*

inline class KtorVersion(val value: String) {
    companion object {
        val stable = KtorVersion("1.2.6")
        val rc = KtorVersion("1.3.0-rc2")
    }
}

class KtorDependencies(version: KtorVersion) :
    GroupWithVersionPlatforms by group("io.ktor", provider).version(version.value).platforms(jvm("jvm"), common()) {

    companion object {
        val provider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/ktor")
    }

    val client by lazy(::Client)

    inner class Client {
        val core = artifact("ktor-client-core")
        val websockets = artifact("ktor-client-websockets")
        val cio = artifact("ktor-client-cio").jvm
    }
}
