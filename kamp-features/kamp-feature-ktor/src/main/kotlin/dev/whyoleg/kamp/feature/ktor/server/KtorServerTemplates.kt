package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerTemplates(version: String = Ktor.defaultVersion) {
    private val jvm = KtorGroup.version(version).platforms(KampPlatform.jvm)
    private fun feature(name: String) = jvm.artifact("ktor-$name")

    val mustache = feature("mustache")
    val freemarker = feature("freemarker")
    val pebble = feature("pebble")
    val thymeleaf = feature("thymeleaf")
    val velocity = feature("velocity")
}
