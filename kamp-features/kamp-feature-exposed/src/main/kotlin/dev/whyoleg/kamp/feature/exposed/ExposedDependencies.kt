package dev.whyoleg.kamp.feature.exposed

import dev.whyoleg.kamp.dependency.*

class ExposedDependencies(version: String = Exposed.defaultVersion) {
    private val jvm = ExposedGroup.version(version).platforms(KampPlatform.jvm)
    val core = jvm.artifact("exposed-core")
    val dao = jvm.artifact("exposed-dao")
    val jdbc = jvm.artifact("exposed-jdbc")

    val time = ExposedTime(version)
    val spring = ExposedSpring(version)
}
