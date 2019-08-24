package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.module.*
import org.gradle.api.initialization.*
import java.io.*

class KampSettings {
    private val modules: MutableSet<Module> = mutableSetOf()

    fun modules(root: RootModule) {
        modules += root.also(RootModule::lazy).all()
    }

    internal fun configure(settings: Settings) {
        val moduleNames = modules.map(Module::name)
        settings.include(*moduleNames.toTypedArray())
        modules.forEach { (name, path) ->
            path?.let { settings.project(name).projectDir = File("${settings.rootDir.absolutePath}/$it") }
        }

    }
}