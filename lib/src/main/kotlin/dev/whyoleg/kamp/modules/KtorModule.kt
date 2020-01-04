package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.options.*
import dev.whyoleg.kamp.platform.KampPlatform.*

inline class KtorVersion(val value: String) {
    companion object {
        val Stable = KtorVersion("1.2.6")
        val RC = KtorVersion("1.3.0-rc2")
    }
}

class KtorDependencies(version: KtorVersion) :
    GroupWithVersionPlatforms by group("io.ktor", Provider).version(version.value).platforms(jvm("jvm"), common()) {
    companion object {
        val Provider = RepositoryProviders.bintray("kotlin", "ktor")
        val Stable: KtorDependencies by lazy { KtorDependencies(KtorVersion.Stable) }
        val RC: KtorDependencies by lazy { KtorDependencies(KtorVersion.RC) }
    }

    val client by lazy(::Client)

    inner class Client {
        val core = artifact("ktor-client-core")
        val websockets = artifact("ktor-client-websockets")
        val cio = artifact("ktor-client-cio").jvm
    }
}

object KtorExperimentalAnnotations {
    val KtorExperimentalAPI = ExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
}
