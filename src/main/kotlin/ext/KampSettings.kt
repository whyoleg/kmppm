package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.module.*
import dev.whyoleg.kamp.plugin.*
import org.gradle.api.initialization.*
import java.io.*

class KampSettings {
    private val plugins: MutableSet<Plugin> = mutableSetOf()
    private val modules: MutableSet<Module> = mutableSetOf()

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun modules(root: RootModule) {
        modules += root.also(RootModule::lazy).all()
    }

    internal fun configure(settings: Settings) {
        settings.pluginManagement {
            it.repositories { handler -> plugins.forEach { it.classpath?.provider?.invoke(handler) } }
            it.resolutionStrategy.eachPlugin { details ->
                println("CONFIGURE: ${details.requested.id.id}")
                plugins.find { it.name == details.requested.id.id }?.classpath?.let {
                    println("Found: $it")
                    details.useModule(it.string())
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