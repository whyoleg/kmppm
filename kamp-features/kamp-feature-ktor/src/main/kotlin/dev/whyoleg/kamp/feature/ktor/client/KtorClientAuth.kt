package dev.whyoleg.kamp.feature.ktor.client

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorClientAuth(version: String) : KampDependency by KtorGroup.client(version).artifact("ktor-client-auth") {
    val basic = artifact("ktor-client-auth-basic")
}
