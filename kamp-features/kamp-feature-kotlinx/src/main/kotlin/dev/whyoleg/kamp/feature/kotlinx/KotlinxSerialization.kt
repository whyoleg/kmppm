package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.kotlinx.KotlinxVersions.Companion.Default

class KotlinxSerialization(version: String = Default.serialization) : KampDependency by KotlinxGroup.serialization(version) {
    val protobuf = artifact("kotlinx-serialization-protobuf")
    val cbor = artifact("kotlinx-serialization-cbor")
    val properties = artifact("kotlinx-serialization-properties")
    val configParser = artifact("kotlinx-serialization-runtime-configparser").platforms(KampPlatform.jvm)
}
