package dev.whyoleg.kamp.feature.ktor.client

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorClient(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.client(version) {
    val tests = artifact("ktor-client-tests")

    val engines = KtorClientEngines(version)
    val features = KtorClientFeatures(version)
}
