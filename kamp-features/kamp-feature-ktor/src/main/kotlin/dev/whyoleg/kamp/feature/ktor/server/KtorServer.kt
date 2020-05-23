package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServer(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.server(version) {

    val host = artifact("ktor-server-tests")
    val tests = artifact("ktor-server-host-common")
    val testHost = artifact("ktor-server-test-host")
    val servlet = artifact("ktor-server-servlet")

    val engines = KtorServerEngines(version)
    val features = KtorServerFeatures(version)
}
