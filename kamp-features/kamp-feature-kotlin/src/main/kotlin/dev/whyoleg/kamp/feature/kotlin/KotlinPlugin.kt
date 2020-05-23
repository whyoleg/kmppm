package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

class KotlinPlugin(version: String = Kotlin.defaultVersion) : KampDependency by KotlinGroup.plugin(version) {
    val serialization = artifact("kotlin-serialization")
    val allOpen = artifact("kotlin-allopen")
}
