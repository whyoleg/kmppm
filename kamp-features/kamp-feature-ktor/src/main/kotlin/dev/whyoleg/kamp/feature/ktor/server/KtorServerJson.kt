package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerJson(version: String = Ktor.defaultVersion) {
    private val jvm = KtorGroup.version(version).platforms(KampPlatform.jvm)
    private fun feature(name: String) = jvm.artifact("ktor-$name")

    val serialization = feature("serialization")
    val gson = feature("gson")
    val jackson = feature("jackson")
}
