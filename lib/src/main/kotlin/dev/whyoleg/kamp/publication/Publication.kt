package dev.whyoleg.kamp.publication

import org.gradle.api.*

data class License(
    val name: String,
    val url: String,
    val disrtribution: String? = null
) {
    companion object {
        val apache2: License = License("Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0.txt", "repo")
    }
}

data class Developer(
    val id: String,
    val name: String,
    val email: String? = null,
    val organization: String? = null,
    val organizationUrl: String? = null
)

data class Publication(
    val name: String,
    val description: String,
    val licenses: List<License> = emptyList(),
    val developers: List<Developer> = emptyList(),
    val labels: List<String> = emptyList(),
    val url: String? = null,
    val vcsUrl: String? = null
)

internal fun XmlProvider.configurePublication(publication: Publication) {
    asNode().apply {

        appendNode("name", publication.name)
        appendNode("description", publication.description)

        publication.url?.let { appendNode("url", it) }

        if (publication.licenses.isNotEmpty()) {
            val licenses = appendNode("licenses")
            publication.licenses.forEach {
                licenses.appendNode("license").apply {
                    appendNode("name", it.name)
                    appendNode("url", it.url)
                    it.disrtribution?.let { appendNode("distribution", it) }
                }
            }
        }
        if (publication.developers.isNotEmpty()) {
            val developers = appendNode("developers")
            publication.developers.forEach {
                developers.appendNode("developer").apply {
                    appendNode("id", it.id)
                    appendNode("name", it.name)
                    it.email?.let { appendNode("email", it) }
                    it.organization?.let { appendNode("organization", it) }
                    it.organizationUrl?.let { appendNode("organizationUrl", it) }
                }
            }
        }
        publication.vcsUrl?.let {
            appendNode("scm").apply {
                appendNode("url", it)
            }
        }
    }
}
