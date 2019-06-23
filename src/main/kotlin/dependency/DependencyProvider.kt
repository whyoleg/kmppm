package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.dsl.*

typealias DependencyProvider = RepositoryHandler.() -> Unit
