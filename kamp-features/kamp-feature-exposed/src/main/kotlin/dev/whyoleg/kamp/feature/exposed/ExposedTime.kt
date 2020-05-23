package dev.whyoleg.kamp.feature.exposed

import dev.whyoleg.kamp.dependency.*

class ExposedTime(version: String = Exposed.defaultVersion) {
    private val jvm = ExposedGroup.version(version).platforms(KampPlatform.jvm)
    val java = jvm.artifact("exposed-java-time")
    val joda = jvm.artifact("exposed-jodatime")
}
