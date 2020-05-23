package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

class KotlinDependencies(version: String = Kotlin.defaultVersion) {
    val stdlib = KotlinStdlib(version)
    val plugin = KotlinPlugin(version)
    val test = stdlib.artifact("kotlin-test")
    val reflect = KotlinGroup.version(version).artifact("kotlin-reflect").platforms(KampPlatform.jvm)
    val annotations = KotlinTestAnnotations(version)
}
