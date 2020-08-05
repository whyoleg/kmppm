package dev.whyoleg.kamp.publication

import org.gradle.api.artifacts.dsl.*

interface Publisher<ProviderOptions, PublishOptions> {
    val defaultProviderOptions: ProviderOptions
    val defaultPublishOptions: PublishOptions

    fun provide(repositoryHandler: RepositoryHandler, options: ProviderOptions = defaultProviderOptions)
    fun publish(options: PublishOptions = defaultPublishOptions)
}

inline fun <T> Publisher<T, *>.provide(repositoryHandler: RepositoryHandler, block: T.() -> Unit) {
    provide(repositoryHandler, defaultProviderOptions.apply(block))
}

inline fun <T> Publisher<*, T>.publish(block: T.() -> Unit) {
    publish(defaultPublishOptions.apply(block))
}
