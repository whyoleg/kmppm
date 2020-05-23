package dev.whyoleg.kamp.feature.ktor.server

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.ktor.*

class KtorServerAuth(version: String) : KampDependency by KtorGroup.server(version).artifact("ktor-auth") {
    val jwt = artifact("ktor-auth-jwt")
    val ldap = artifact("ktor-auth-ldap")
}
