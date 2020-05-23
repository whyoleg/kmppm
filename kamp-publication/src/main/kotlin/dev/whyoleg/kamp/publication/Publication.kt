package dev.whyoleg.kamp.publication

import org.gradle.api.publish.maven.*

data class Publication(
    val name: String,
    val description: String,
    val url: String? = null,
    val licenses: List<License> = emptyList(),
    val developers: List<Developer> = emptyList(),
    val vcsUrl: String? = null
)

@PublishedApi
internal fun MavenPom.configure(publication: Publication) {
    name.set(publication.name)
    description.set(publication.description)
    publication.url?.let(url::set)

    licenses(publication.licenses)
    developers(publication.developers)

    publication.vcsUrl?.let { vcsUrl ->
        scm { it.url.set(vcsUrl) }
    }
}

//organization
//contributors
//scm
//issueManagement
//ciManagement
//distributionManagement
//mailingLists
