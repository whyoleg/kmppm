package dev.whyoleg.kamp.feature.ktor

import dev.whyoleg.kamp.dependency.*

object KtorGroup : Group by group("io.ktor") {
    fun http(version: String = Ktor.defaultVersion): KampDependency =
        artifact("ktor-http").version(version).platforms(KampPlatform.newStyle)

    fun network(version: String = Ktor.defaultVersion): KampDependency =
        artifact("ktor-network").version(version).platforms(KampPlatform.jvm)

    fun client(version: String = Ktor.defaultVersion): KampDependency =
        artifact("ktor-client-core").version(version).platforms(KampPlatform.newStyle)

    fun server(version: String = Ktor.defaultVersion): KampDependency =
        artifact("ktor-server-core").version(version).platforms(KampPlatform.jvm)
}
