package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.module.*
import dev.whyoleg.kamp.plugin.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.initialization.*
import java.io.*

class KampSettings(private val settings: Settings) {
    private val plugins: MutableSet<Plugin> = mutableSetOf()
    private val modules: MutableSet<Module> = mutableSetOf()
    private val repositoryBlocks: MutableSet<RepositoryHandler.() -> Unit> = mutableSetOf()

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun modules(vararg modules: Module) {
        this.modules += modules
    }

    fun modules(modules: Iterable<Module>) {
        this.modules += modules
    }

    fun repositories(block: RepositoryHandler.() -> Unit) {
        repositoryBlocks += block
    }

    fun configure() {
        settings.pluginManagement {
            it.repositories { handler ->
                repositoryBlocks.forEach { handler.it() }
            }
            it.resolutionStrategy.eachPlugin { details ->
                plugins.forEach { (name, classpath) ->
                    classpath?.takeIf { details.requested.id.id == name }?.let { raw ->
                        details.useModule(raw.string())
                    }
                }
            }
        }
        val moduleNames = modules.map(Module::name)
        settings.include(*moduleNames.toTypedArray())
        modules.forEach { (name, path) ->
            path?.let { settings.project(name).projectDir = File(it) }
        }

    }
}