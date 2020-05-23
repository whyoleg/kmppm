package dev.whyoleg.kamp.feature.ktor.client

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorClientEngines(version: String = Ktor.defaultVersion) {
    private val group = KtorGroup.version(version)
    private fun engine(name: String, platform: KampPlatform) = group.artifact("ktor-client-$name").platforms(platform)
    private fun engine(name: String, platforms: Iterable<PlatformPostfix>) = group.artifact("ktor-client-$name").platforms(platforms)

    val jetty = engine("jetty", KampPlatform.jvm)
    val okhttp = engine("okhttp", KampPlatform.jvm)
    val apache = engine("apache", KampPlatform.jvm)
    val cio = engine("cio", KampPlatform.jvm)

    val android = engine("android", KampPlatform.android)

    val ios = engine("ios", KampPlatform.native)
    val curl = engine("curl", KampPlatform.native)

    val js = engine("js", KampPlatform.js)

    val mock = engine("mock", KampPlatform.newStyle)
}
