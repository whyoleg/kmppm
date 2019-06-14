package dev.whyoleg.kamp.ext

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

    fun modules(root: RootModule) {
        modules += root.also(RootModule::lazy).all()
    }

    fun repositories(block: RepositoryHandler.() -> Unit) {
        repositoryBlocks += block
    }

    fun configure() {
        println("PLUGINS: \n${plugins.joinToString("\n")}")
        settings.pluginManagement {
            it.repositories { handler ->
                (repositoryBlocks + plugins.map(Plugin::repositoryProvider)).forEach {
                    println(it)
                    handler.it()
                }
            }
            it.resolutionStrategy.eachPlugin { details ->
                println("CONFIGURE: ${details.requested.id.id}")
                plugins.forEach { (name, classpath) ->
                    println("TRY: $name with $classpath")
                    classpath?.takeIf { details.requested.id.id == name }?.let { raw ->
                        println(name to classpath)
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