package dev.whyoleg.kamp.feature.ktor

import dev.whyoleg.kamp.dependency.*

class KtorHttp(version: String = Ktor.defaultVersion) : KampDependency by KtorGroup.http(version) {
    val cio = artifact("ktor-http-cio")
}
