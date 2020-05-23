package dev.whyoleg.kamp.publication

import org.gradle.api.publish.maven.*

data class Developer(
    val id: String,
    val name: String,
    val email: String? = null,
    val url: String? = null,
    val organization: String? = null,
    val organizationUrl: String? = null
)

internal fun MavenPom.developers(developers: List<Developer>) {
    if (developers.isEmpty()) return

    developers {
        developers.forEach { developer ->
            it.developer(developer::configure)
        }
    }
}

private fun Developer.configure(pomDeveloper: MavenPomDeveloper) {
    pomDeveloper.id.set(id)
    pomDeveloper.name.set(name)
    url?.let(pomDeveloper.url::set)
    email?.let(pomDeveloper.email::set)
    organization?.let(pomDeveloper.organization::set)
    organizationUrl?.let(pomDeveloper.organizationUrl::set)
}
