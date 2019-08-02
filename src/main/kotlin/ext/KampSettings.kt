package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.module.*
import dev.whyoleg.kamp.plugin.*
import org.gradle.api.initialization.*
import java.io.*

class KampSettings(versions: BuiltInVersions) : KampBase(versions) {
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
            it.plugins { spec -> plugins.forEach { spec.id(it.name).version(it.classpath?.version) } }
        }

        val moduleNames = modules.map(Module::name)
        settings.include(*moduleNames.toTypedArray())
        modules.forEach { (name, path) ->
            path?.let { settings.project(name).projectDir = File("${settings.rootDir.absolutePath}/$it") }
        }

    }
}