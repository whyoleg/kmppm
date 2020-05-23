package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerMetrics(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.server(version).artifact("ktor-metrics") {
    val micrometer = artifact("ktor-metrics-micrometer")
}
