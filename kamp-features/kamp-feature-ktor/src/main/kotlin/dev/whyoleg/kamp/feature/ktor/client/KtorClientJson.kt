package dev.whyoleg.kamp.feature.ktor.client

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorClientJson(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.client(version).artifact("ktor-client-json") {
    private fun feature(name: String) = artifact("ktor-client-$name")

    val serialization = feature("serialization")
    val tests = feature("json-tests").platforms(KampPlatform.jvm)
    val gson = feature("gson").platforms(KampPlatform.jvm)
    val jackson = feature("jackson").platforms(KampPlatform.jvm)
}
