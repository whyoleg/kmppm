package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerEngines(version: String = Ktor.defaultVersion) {
    private val jvm = KtorGroup.version(version).platforms(KampPlatform.jvm)
    private fun engine(name: String) = jvm.artifact("ktor-server-$name")

    val cio = engine("cio")
    val jetty = engine("jetty")
    val netty = engine("netty")
    val tomcat = engine("tomcat")
}
