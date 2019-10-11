package dev.whyoleg.kamp.publishing

import dev.whyoleg.kamp.configuration.*

class PublishersBuilder internal constructor(private val configuration: ProjectConfiguration) {
    internal val publishers: MutableList<Publisher> = mutableListOf()
    internal val publications: MutableList<Publication> = mutableListOf()

    fun bintray(publication: Publication, block: BintrayPublisher.() -> Unit) {
        publications += publication
        publishers += BintrayPublisher(publication, configuration).apply(block)
    }

}
