package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

class KotlinStdlib(version: String = Kotlin.defaultVersion) : KampDependency by KotlinGroup.stdlib(version) {
    val jdk7 = artifact("kotlin-stdlib-jdk7").platforms(KampPlatform.jvm)
    val jdk8 = artifact("kotlin-stdlib-jdk8").platforms(KampPlatform.jvm)
}
