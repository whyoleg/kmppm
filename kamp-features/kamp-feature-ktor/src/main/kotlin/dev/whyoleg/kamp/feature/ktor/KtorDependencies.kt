package dev.whyoleg.kamp.feature.ktor

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.client.*
import dev.whyoleg.kamp.feature.ktor.server.*

class KtorDependencies(version: String = Ktor.defaultVersion) {
    private val newStyle = KtorGroup.version(version).platforms(KampPlatform.newStyle)

    val io = newStyle.artifact("ktor-io")
    val utils = newStyle.artifact("ktor-utils")
    val testDispatcher = newStyle.artifact("ktor-test-dispatcher")

    val network = KtorNetwork(version)
    val http = KtorHttp(version)

    val client = KtorClient(version)
    val server = KtorServer(version)
}
