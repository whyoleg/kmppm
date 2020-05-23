package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerFeatures(version: String = Ktor.defaultVersion) {
    private val jvm = KtorGroup.version(version).platforms(KampPlatform.jvm)
    private fun feature(name: String) = jvm.artifact("ktor-$name")

    val locations = feature("locations")
    val sessions = feature("server-sessions")
    val html = feature("html-builder")
    val webjars = feature("webjars")
    val websockets = feature("websockets")

    val auth = KtorServerAuth(version)
    val json = KtorServerJson(version)
    val metrics = KtorServerMetrics(version)
    val templates = KtorServerTemplates(version)
}
