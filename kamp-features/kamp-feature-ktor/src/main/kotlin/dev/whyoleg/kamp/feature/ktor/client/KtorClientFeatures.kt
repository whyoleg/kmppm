package dev.whyoleg.kamp.feature.ktor.client

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorClientFeatures(version: String = Ktor.defaultVersion) {
    private val newStyle = KtorGroup.version(version).platforms(KampPlatform.newStyle)
    private fun feature(name: String) = newStyle.artifact("ktor-client-$name")

    val auth = KtorClientAuth(version)
    val encoding = feature("encoding")
    val logging = feature("logging")
    val websockets = feature("websockets")

    val json = KtorClientJson("json")
}
