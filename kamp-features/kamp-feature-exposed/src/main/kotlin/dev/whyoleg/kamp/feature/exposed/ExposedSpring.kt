package dev.whyoleg.kamp.feature.exposed

import dev.whyoleg.kamp.dependency.*

class ExposedSpring(version: String = Exposed.defaultVersion) {
    private val jvm = ExposedGroup.version(version).platforms(KampPlatform.jvm)
    val starter = jvm.artifact("exposed-spring-boot-starter")
    val transaction = jvm.artifact("spring-transaction")
}
