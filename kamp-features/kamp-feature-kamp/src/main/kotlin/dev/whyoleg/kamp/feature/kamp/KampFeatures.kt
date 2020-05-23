package dev.whyoleg.kamp.feature.kamp

import dev.whyoleg.kamp.dependency.*

object KampFeatures {
    private fun feature(name: String) = KampGroup.artifact("kamp-feature-$name")

    val android = feature("android")
    val exposed = feature("exposed")
    val gradle = feature("gradle")
    val jib = feature("jib")
    val kotlin = feature("kotlin")
    val kotlinx = feature("kotlinx")
    val ktor = feature("ktor")
    val logging = feature("logging")
    val shadow = feature("shadow")
    val updates = feature("updates")
    val kamp = feature("kamp")
}
