package dev.whyoleg.kamp.feature.ktor

import dev.whyoleg.kamp.dependency.*

class KtorNetwork(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.network(version) {
    val tls = artifact("ktor-network-tls")
    val certificates = artifact("ktor-network-tls-certificates")
}
