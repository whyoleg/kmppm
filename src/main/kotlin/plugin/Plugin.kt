package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*
import org.gradle.api.artifacts.dsl.*

data class Plugin(val name: String, val classpath: RawDependency?, val repositoryProvider: RepositoryHandler.() -> Unit = {}) {
    fun version(version: String): Plugin = copy(classpath = classpath?.copy(version = version))
}