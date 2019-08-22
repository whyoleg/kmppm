package dev.whyoleg.kamp.publishing

import dev.whyoleg.kamp.*

class PublishersBuilder internal constructor(
    private val configuration: ProjectConfiguration,
    private val builtIn: BuiltIn
) {
    internal val publishers: MutableList<Publisher> = mutableListOf()
    internal val publications: MutableList<Publication> = mutableListOf()

    fun bintray(publication: Publication, block: BintrayPublisher.() -> Unit) {
        publications += publication
        publishers += BintrayPublisher(publication, configuration, builtIn).apply(block)
    }

}